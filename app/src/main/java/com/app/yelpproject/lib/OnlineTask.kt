package com.app.yelpproject.lib

import android.os.AsyncTask

class OnlineTask(
    private val processing: () -> Unit, private val progressVisible: ((Boolean) -> Unit)? = null,
    private val exceptionHandler: ((Exception) -> Unit)? = null
) {

    companion object {
        fun create(
            processing: () -> Unit, progressVisible: ((Boolean) -> Unit)? = null,
            exceptionHandler: ((Exception) -> Unit)? = null
        ) =
            OnlineTask(processing, progressVisible, exceptionHandler)
    }

    fun execute() {
        Task(processing, progressVisible, exceptionHandler).execute()
    }

    private class Task(
        private val processing: () -> Unit,
        private val progressVisible: ((Boolean) -> Unit)? = null,
        private val exceptionHandler: ((Exception) -> Unit)? = null
    ) :
        AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            try {
                processing.invoke()
            } catch (e: Exception) {
                exceptionHandler?.invoke(e)
                progressVisible?.invoke(false)
                e.printStackTrace()
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressVisible?.invoke(true)
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            progressVisible?.invoke(false)
        }
    }
}