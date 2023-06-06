
package com.alican.mvvm_starter.util.utils

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

fun generateSharingLink(
    deepLink: Uri,
    previewImageLink: Uri,
    previewTitle: String,
    previewDesc: String,
    getShareableLink: (String) -> Unit = {},
) {
    FirebaseDynamicLinks.getInstance().createDynamicLink().run {
        // What is this link parameter? You will get to know when we will actually use this function.
        link = deepLink

        // [domainUriPrefix] will be the domain name you added when setting up Dynamic Links at Firebase Console.
        // You can find it in the Dynamic Links dashboard.
        domainUriPrefix = DeepLink.PREFIX

        // Pass your preview Image Link here;
        setSocialMetaTagParameters(
            DynamicLink.SocialMetaTagParameters.Builder()
                .setImageUrl(previewImageLink)
                .setDescription(previewDesc)
                .setTitle(previewTitle)
                .build()
        )

        // Finally
        buildShortDynamicLink()
    }.also {
        it.addOnSuccessListener { dynamicLink ->
            // This lambda will be triggered when short link generation is successful

            // Retrieve the newly created dynamic link so that we can use it further for sharing via Intent.
            getShareableLink.invoke(dynamicLink.shortLink.toString())
        }
        it.addOnFailureListener {
            // This lambda will be triggered when short link generation failed due to an exception

            // Handle
        }
    }
}

fun Fragment.shareDeepLink(deepLink: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(
        Intent.EXTRA_SUBJECT,
        "Bu ürüne göz at ->"
    )
    intent.putExtra(Intent.EXTRA_TEXT, deepLink)
    if (isAdded) {
        requireContext().startActivity(intent)
    }
}