package com.example.eletriccarapps.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.eletriccarapps.data.local.CarrosContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.eletriccarapps.data.local.CarrosContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.eletriccarapps.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.eletriccarapps.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.eletriccarapps.data.local.CarrosContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.eletriccarapps.data.local.CarrosContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.example.eletriccarapps.domain.Carro

class CarLocalRepository(private val context: Context) {

    fun save(carro: Carro): Boolean {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME_CAR_ID, carro.id)
            put(COLUMN_NAME_PRECO, carro.preco)
            put(COLUMN_NAME_BATERIA, carro.bateria)
            put(COLUMN_NAME_POTENCIA, carro.potencia)
            put(COLUMN_NAME_RECARGA, carro.recarga)
            put(COLUMN_NAME_URL_PHOTO, carro.urlPhoto)
        }

        val inserted = db.insert(CarrosContract.CarEntry.TABLE_NAME, null, values)
        return inserted != -1L
    }

    fun delete(carro: Carro): Boolean {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase
        val deleted = db.delete(
            CarrosContract.CarEntry.TABLE_NAME,
            "$COLUMN_NAME_CAR_ID = ?",
            arrayOf(carro.id.toString())
        )
        return deleted > 0
    }

    fun findCarById(id: Int): Carro {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME,
            arrayOf(
                BaseColumns._ID,
                COLUMN_NAME_CAR_ID,
                COLUMN_NAME_PRECO,
                COLUMN_NAME_BATERIA,
                COLUMN_NAME_POTENCIA,
                COLUMN_NAME_RECARGA,
                COLUMN_NAME_URL_PHOTO
            ),
            "$COLUMN_NAME_CAR_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        var carro = Carro(0, "", "", "", "", "", false)

        if (cursor.moveToFirst()) {
            carro = Carro(
                id = cursor.getInt(1),
                preco = cursor.getString(2),
                bateria = cursor.getString(3),
                potencia = cursor.getString(4),
                recarga = cursor.getString(5),
                urlPhoto = cursor.getString(6),
                isFavorite = true
            )
        }

        cursor.close()
        return carro
    }

    fun getAll(): List<Carro> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME,
            arrayOf(
                BaseColumns._ID,
                COLUMN_NAME_CAR_ID,
                COLUMN_NAME_PRECO,
                COLUMN_NAME_BATERIA,
                COLUMN_NAME_POTENCIA,
                COLUMN_NAME_RECARGA,
                COLUMN_NAME_URL_PHOTO
            ),
            null, null, null, null, null
        )

        val carros = mutableListOf<Carro>()

        while (cursor.moveToNext()) {
            carros.add(
                Carro(
                    id = cursor.getInt(1),
                    preco = cursor.getString(2),
                    bateria = cursor.getString(3),
                    potencia = cursor.getString(4),
                    recarga = cursor.getString(5),
                    urlPhoto = cursor.getString(6),
                    isFavorite = true
                )
            )
        }

        cursor.close()
        return carros
    }

    fun saveIfNotExist(carro: Carro) {
        val car = findCarById(carro.id)
        if (car.id == ID_WHEN_NO_CAR) {
            save(carro)
        }
    }

    companion object {
        const val ID_WHEN_NO_CAR = 0
    }
}
