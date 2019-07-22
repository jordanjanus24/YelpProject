package com.app.yelpproject

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.yelpproject.adapter.BusinessAdapter
import com.app.yelpproject.constants.BusinessConstants
import com.app.yelpproject.constants.filters.BusinessFilter
import com.app.yelpproject.intent.IntentFactory
import com.app.yelpproject.lib.DoubleTapBackPressedHandler
import com.app.yelpproject.lib.Permission
import com.app.yelpproject.lib.animator.execAnimation
import com.app.yelpproject.lib.animator.executeFloatAnimator
import com.app.yelpproject.lib.bottomsheet.addCallback
import com.app.yelpproject.lib.bottomsheet.collapseSheet
import com.app.yelpproject.lib.bottomsheet.expandSheet
import com.app.yelpproject.lib.bottomsheet.isExpanded
import com.app.yelpproject.lib.dialogs.AlertDialogObject
import com.app.yelpproject.lib.dialogs.button.DialogButton
import com.app.yelpproject.lib.ktx.*
import com.app.yelpproject.lib.maps.GoogleMapsAPI
import com.app.yelpproject.lib.maps.newMarker
import com.app.yelpproject.lib.maps.onInfoWindowTag
import com.app.yelpproject.lib.maps.zoomCoordinates
import com.app.yelpproject.lib.utils.*
import com.app.yelpproject.view_model.BusinessViewModel
import com.app.yelpproject.views.dialogs.AddressFilterBottomView
import com.app.yelpproject.views.dialogs.CountrySelector
import com.app.yelpproject.views.dialogs.CuisineOptionsBottomView
import com.app.yelpproject.views.dialogs.InfoMarkerBottomView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yelp.fusion.client.models.Business
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_view.*
import kotlinx.android.synthetic.main.business_item.view.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var gMap: GoogleMap? = null
    private var sheetBehavior: BottomSheetBehavior<out View>? = null
    private val businessVM by lazy { ViewModelProviders.of(this).get(BusinessViewModel::class.java) }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        noLimits(true)
        toolbar.setOnClickReference(this::expandSearch)
        toolbar.setOnNavigationClickReference(this::expandSearch)
        bottomSheet.isVisible = false
        noInternetConnection.isVisible = false
        progress.isVisible = false
        init()
    }

    private fun loadDetail(view: View, business: Business) {
        var intent = Intent(this, DetailsActivity::class.java)
        intent = IntentFactory.set(intent, business)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Fade(Fade.IN)
            window.exitTransition = Fade(Fade.OUT)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view.imgIcon.getPair(),
                view.txtBusinessName.getPair(),
                view.txtCuisineType.getPair(),
                view.rating.getPair()
            )
            ActivityCompat.startActivity(this, intent, options.toBundle())
        } else startActivity(intent)
    }

    private fun requestPermission() {
        if (Permission.isExisting(applicationContext, PERMISSIONS)) {
            fetchDataset()
        } else {
            Permission.request(this, PERMISSIONS, REQUEST_PERMISSION) {
                AlertDialogObject.create(
                    this,
                    null,
                    "To make the application work, this permission is required.",
                    DialogButton("Proceed") {
                        Permission.requestPermission(this, PERMISSIONS, REQUEST_PERMISSION)
                    },
                    DialogButton("Cancel")
                )
            }
        }
    }

    private fun setupMap() = GoogleMapsAPI.createMap(this, R.id.map)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) fetchDataset()
            else requestPermission()
        }
    }


    private fun init() {
        searchFilters.isVisible = false
        sheetBehavior = BottomSheetBehavior.from(bottomSheet).addCallback(this::setAppbarColor) {
            when (it) {
                BottomSheetBehavior.STATE_EXPANDED -> scrollSheet.isScrollable = true
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    scrollSheet.smoothScrollYAnimate(0)
                    scrollSheet.isScrollable = false
                    if (searchMenu?.isActionViewExpanded!!) {
                        bottomSheetState = BottomSheetBehavior.STATE_COLLAPSED
                        searchMenu?.collapse()
                    }
                }
                BottomSheetBehavior.STATE_DRAGGING -> {
                    if (bottomSheet.scrollY != 0) sheetBehavior?.expandSheet()
                }
            }
            setLightStatusBarIcons((sheetBehavior?.isExpanded()!!))
        }
        sortBy.setOnButtonCheckedReference(this::addSortedList)
        noInternetConnection.setOnClickReference(this::fetchDataset)
        searchByCuisine.setOnClickReference(this::showCuisineFilters)
        filterAddress.setOnClickReference(this::showAddressFilters)
        setupMap()
        requestPermission()
        businessVM.currentCountry.observe(this, Observer { toolbar.title = "Search $it" })
    }

    private fun showCountrySelector() =
        CountrySelector.show(this, businessVM.currentCountry.value!!, businessVM::getListByCountry)

    private var filterCuisine = ArrayList<String>()
    @SuppressLint("InflateParams")
    private fun showCuisineFilters() = CuisineOptionsBottomView.show(this, filterCuisine, baseList!!) {
        filterCuisine = it
        loadList(baseList!!)
    }

    private var addressFilter: String = ""
    private fun showAddressFilters() = AddressFilterBottomView.show(this, addressFilter) {
        addressFilter = it
        loadList(baseList!!)
    }

    private var sortedList: Comparator<Business>? = null
    private fun addSortedList() {
        sortedList = null
        sortBy.checkedButtonIds.forEach {
            sortedList = when (it) {
                R.id.sortByDistance -> sortedList.sortWith(Business::getDistance)
                R.id.sortByRatings -> sortedList.sortWith(Business::getRating, true)
                R.id.sortByReviewCount -> sortedList.sortWith(Business::getReviewCount, true)
                else -> null
            }
        }
        loadList(baseList!!)
    }

    private var appBarStartColor: Int = android.R.color.transparent
    private var appBarEndColor: Int = R.color.colorPrimary
    private fun setAppbarColor(slideOffset: Float) =
        appbar.setBackgroundColor(
            ColorUtil.interpolateColorsFromRes(
                applicationContext,
                slideOffset,
                appBarStartColor,
                appBarEndColor
            )
        )

    private fun fetchDataset() = businessVM.init(LocationUtil.getCurrentCountryFromTelephony(applicationContext))

    override fun onMapReady(googleMap: GoogleMap?) {
        this.gMap = googleMap
        gMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        businessVM.progressVisible.observe(this, Observer {
            progress.isVisible = it
            if (businessVM.catchedException.value == null) {
                toolbar.isEnabled = !it
                countriesMenu?.isEnabled = !it
                if (it == true) bottomSheet.execAnimation(R.anim.original_to_bottom)
                else bottomSheet.execAnimation(R.anim.bottom_to_original)
                bottomSheet.isVisible = !it
                noLimits(it)
            }
        })
        businessVM.catchedException.observe(this, Observer {
            if (it == null) noInternetConnection.execAnimation(R.anim.original_to_bottom)
            else noInternetConnection.execAnimation(R.anim.bottom_to_original)
            noInternetConnection.isVisible = (it != null)
            noLimits(true)
        })
        businessVM.businessList.observe(this, Observer { list ->
            noLimits(false)
            loadMapCoordinates(list)
            baseList = list
            adapter = BusinessAdapter(list!!, this::loadDetail)
            loadList(list)
            businessList.loadAdapter(adapter!!)
            sortBy.isEnabled = true
            sortBy.clearChecked()
            scrollSheet.isScrollable = false
            scrollSheet.smoothScrollYAnimate(0)
        })
    }

    private fun loadMapCoordinates(list: List<Business>) {
        gMap?.clear()
        list.forEachIndexed { index, it ->
            val coordinates = BusinessConstants.latLngCoordinates(it.coordinates)
            if (index == 0) {
                delayedAction(2000) {
                    try {
                        val cityCoordinates = LocationUtil.getLatLngFromLocation(applicationContext, it.location.city)
                        gMap?.zoomCoordinates(cityCoordinates!!)
                    } catch (ex: Exception) {
                        try {
                            gMap?.zoomCoordinates(coordinates)
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                }
            }
            gMap?.newMarker(
                coordinates,
                it.name,
                BusinessConstants.displayAddress(it.location)
            )?.tag = it.id
            gMap?.onInfoWindowTag(this::showInfoOnMarkerClick)
        }
    }

    private fun showInfoOnMarkerClick(id: String) =
        InfoMarkerBottomView.show(this, baseList?.findData { it.id == id }!!, this::loadDetail)

    private var adapter: BusinessAdapter? = null
    private var baseList: List<Business>? = null
    private fun loadList(list: List<Business>) =
        adapter?.setItems(
            sortList(
                BusinessFilter.apply(
                    list,
                    searchView?.query.toString(),
                    filterCuisine,
                    addressFilter
                )
            )
        )

    private fun sortList(baseArr: List<Business>) =
        if (sortedList != null)
            ArrayList(baseArr.sortedWith(sortedList!!))
        else baseArr

    private val doubleTap by lazy { DoubleTapBackPressedHandler(this) }
    override fun onBackPressed(): Unit =
        when {
            sheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED -> sheetBehavior?.collapseSheet()!!
            else -> doubleTap.onTapBackPressed()
        }

    private var searchMenu: MenuItem? = null
    private var countriesMenu: MenuItem? = null
    private var searchView: SearchView? = null
    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searchMenu = menu?.getMenuItem(R.id.app_bar_search, false)
        countriesMenu = menu?.getMenuItem(R.id.action_select_country)
        searchView = searchMenu?.getSearchView("Search Business Name", false, false)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView?.setIconifiedByDefault(true)
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchMenu?.onActionExpandCollapseListener({
            if (sheetBehavior?.isExpanded()!!) {
                executeFloatAnimator(0.1f, 1.0f, this::setAppbarColor, 300,
                    { appBarStartColor = R.color.colorAccent })
                { appBarStartColor = android.R.color.transparent }
            }
            appBarEndColor = R.color.colorPrimary
            searchFilters.isVisible = false
            countriesMenu?.isVisible = true
            addressFilter = ""
            scrollSheet.smoothScrollYAnimate(0)
            sheetBehavior?.state = bottomSheetState
            filterCuisine.clear()
            loadList(baseList!!)
        })
        searchView!!.setQueryTextChangeListener {
            loadList(baseList!!)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        when (intent?.action) {
            Intent.ACTION_SEARCH -> {
                val query = searchView?.query.toString() + intent.getStringExtra(SearchManager.QUERY)
                searchView?.setQuery(query, false)
            }
        }
    }

    private fun expandSearch() {
        if (sheetBehavior?.isExpanded()!!) {
            executeFloatAnimator(0.1f, 1.0f, this::setAppbarColor, 300,
                { appBarStartColor = R.color.colorPrimary })
            { appBarStartColor = android.R.color.transparent }
        }
        appBarEndColor = R.color.colorAccent
        countriesMenu?.isVisible = false
        searchMenu?.expandActionView()
        bottomSheetState = sheetBehavior?.state!!
        sheetBehavior?.expandSheet()
        searchFilters.isVisible = true
    }

    private var bottomSheetState: Int = BottomSheetBehavior.STATE_COLLAPSED
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when {
            item?.itemId == R.id.action_select_country -> {
                sheetBehavior?.collapseSheet()
                showCountrySelector()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val REQUEST_PERMISSION = 102
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}
