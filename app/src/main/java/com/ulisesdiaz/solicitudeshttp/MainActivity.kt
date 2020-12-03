package com.ulisesdiaz.solicitudeshttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException

class MainActivity : AppCompatActivity(), CompletadoListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnValidarRed = findViewById<Button>(R.id.btnValidarRed)
        val btnSolicitudHttp = findViewById<Button>(R.id.btnSolicitudHttp)
        val btnSolicitudVolley = findViewById<Button>(R.id.btnSollicitydVolley)
        val btnSolicituddHttpOk = findViewById<Button>(R.id.btnSolicitudHttpOk)

        // Código para validar red
        btnValidarRed.setOnClickListener{
            if (Network.hayRed(this)){
                Toast.makeText(this, "Si hay red", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show();
            }
        }

        // Codigo para realizar la peticion de manera nativa
        btnSolicitudHttp.setOnClickListener{
            if (Network.hayRed(this)){
                // descargar datos
                DescargaURL(this).execute("https://www.google.com")
            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show();
            }
        }

        // Codifo para realizar la peticion a traves de Volley
        btnSolicitudVolley.setOnClickListener{
            if (Network.hayRed(this)){
                solicitudHttpVolley("https://www.google.com")
            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
            }
        }

        // Codifo para realizar la peticion a traves de httpOk
        btnSolicituddHttpOk.setOnClickListener {
            if (Network.hayRed(this)){
                solicitudHttpOkHttp("https://www.google.com")
            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun descargaCompleta(resultado: String?) {
        Log.d("SolicitudHttpNativa", resultado.toString())
    }

    // Metodo para Volley
    private fun solicitudHttpVolley(url:String){

        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->
            try{
                Log.d("SolicitudHttpVolley", response)
            }catch (e:Exception){

            }
        }, Response.ErrorListener {  })

        queue.add(request)
    }

    // Metodo para la libreria okHttp
    private fun solicitudHttpOkHttp(url: String){
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: okhttp3.Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                // Implementar error
            }

            override fun onResponse(call: Call?, response: okhttp3.Response) {
                val result = response.body().string()

                this@MainActivity.runOnUiThread {
                    try {
                        Log.d("solicitudHttpOKHTTP", result)
                    }catch (e:Exception){

                    }
                }
            }
        })
    }
}