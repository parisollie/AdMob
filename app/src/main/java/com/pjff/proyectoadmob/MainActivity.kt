package com.pjff.proyectoadmob

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pjff.proyectoadmob.ui.theme.ProyectoAdMobTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoAdMobTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //vid 239
//                    BannerView(
//                        modifier = Modifier.fillMaxSize(),
                          //este el Id de prueba
//                        adId = "ca-app-pub-3940256099942544/6300978111"
//                    )
                    //Vid 240
                    InterstitialBanner()
                }
            }
        }
    }
}

@Composable
//vid 239, el banner es el más sencillo.
fun BannerView(modifier: Modifier, adId: String) {
    Column(modifier = Modifier) {
        Spacer(modifier = Modifier.size(24.dp))
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            //Nos sale mas rapido el banner con la factoria.
            factory = { context ->
                AdView(context).apply {
                    //definimos el tamaño del size
                    setAdSize(AdSize.BANNER)
                    //el id de nuestro banner tenra su parametro
                    adUnitId = adId
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
//vid 240
@Composable
fun InterstitialBanner() {
    //para que funcione como una activitie
    val activity = LocalContext.current as Activity
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showInterstitial(activity) }) {
            Text(text = "Mostrar Anuncio")
        }
    }
}

fun showInterstitial(activity: Activity) {
    loadInterstitialAd(activity) { interstitialAd ->
        if (interstitialAd != null){
            interstitialAd.show(activity)
        }else{
            Log.d("Error", "Fallo el anuncio")
        }
    }
}

//Vid 240, para cargar el anuncio.
fun loadInterstitialAd(activity: Activity, callback: (InterstitialAd?) -> Unit) {
    val adRequest = AdRequest.Builder().build()

    InterstitialAd.load(
        activity,
        "ca-app-pub-3940256099942544/1033173712",
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                Log.d("Error", error.message)
                callback(null)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                callback(interstitialAd)
            }
        }
    )

}

















