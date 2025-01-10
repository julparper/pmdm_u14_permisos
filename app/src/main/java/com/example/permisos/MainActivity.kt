package com.example.permisos

import android.os.Bundle
import android.widget.Button
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    companion object {
        const val READ_CONTACTS_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btnAceptar)

        btn.setOnClickListener{
            checkReadContactsPermission()
        }
    }

    //Comprueba si tiene permisos
    private fun checkReadContactsPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            //El permiso de acceso a los contactos no está aceptado, se pide
            requestReadConctactsPermission()
        } else {
            //El permiso está aceptado
            //aquí añadiríamos la lógica sobre los contactos
            Toast.makeText(this,"Acceso a la funcionalidad", Toast.LENGTH_SHORT).show()
        }
    }

    //Solicita el permiso
    private fun requestReadConctactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {
            //El usuario ya ha rechazado el permiso anteriormente, debemos indicarle que vaya a ajustes.
            Toast.makeText(this,"Conceda permisos en ajustes", Toast.LENGTH_SHORT).show()
        } else {
            //El usuario nunca ha aceptado ni rechazado, así que le solicitamos que acepte el permiso.
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                Companion.READ_CONTACTS_REQUEST_CODE
            )
        }
    }

    //Este método escucha la respuesta del usuario ante una solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_CONTACTS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //El usuario ha aceptado el permiso, ya no hay que volver a solicitarlo, podemos lanzar la funcionalidad desde aquí.
                    Toast.makeText(this,"Acceso a la funcionalidad una vez aceptado el permiso", Toast.LENGTH_SHORT).show()
                } else {
                    //El usuario ha rechazado el permiso
                    Toast.makeText(this,"Conceda permisos en ajustes", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                // Para aquellos permisos no controlados
            }
        }
    }



}