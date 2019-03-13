package ua.com.dekalo.hometask.ui.utils.svg

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.PictureDrawable

object GlideSvg {

    @SuppressLint("StaticFieldLeak")
    @Volatile
    private lateinit var requestBuilder: GlideRequest<PictureDrawable>

    fun with(context: Context): GlideRequest<PictureDrawable> {

        if (!::requestBuilder.isInitialized) {
            requestBuilder = GlideApp.with(context.applicationContext)
                .`as`(PictureDrawable::class.java)
                .listener(SvgSoftwareLayerSetter())
        }

        return requestBuilder
    }
}