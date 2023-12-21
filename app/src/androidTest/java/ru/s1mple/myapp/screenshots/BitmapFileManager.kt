package ru.s1mple.myapp.screenshots

import android.app.Instrumentation
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

/**
 * Интерфейс для работы с файлами [Bitmap] скриншотов
 *
 * @author Юрманов Роман
 */
interface BitmapFileManager {

    /**
     * Считывает картинку из папки [assets] и преобразует ее в [Bitmap]
     * Считываемый файл должен быть в формате .png
     *
     * @param fileName имя файла для чтения без указания формата
     */
    fun readBitmapFromAssets(fileName: String): Bitmap {
        return BitmapFactory.decodeStream(Instrumentation().context.assets.open("$fileName.png"))
    }

    /**
     * Сохраняет [Bitmap] скриншот экрана в картинку формата png на девайс
     *
     * @param bitmap [Bitmap] скриншот экрана
     * @param destinationPath путь, по которому будет сохранен скриншот, по умолчанию "sdcard/Pictures/screenshots"
     */
    fun saveBitmapToDevice(bitmap: Bitmap, destinationPath: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "screenshots")) {
        destinationPath.mkdir()
        FileOutputStream(File(destinationPath, "$bitmap.png")).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

}