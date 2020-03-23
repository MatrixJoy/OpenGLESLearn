package catnemo.top.airhockey.util

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2018/11/30
 *
 */
object TextResourceReader {

    fun readTextFileFromResource(context: Context, resId: Int): String {
        val body = StringBuilder()
        try {
            val ins = context.resources.openRawResource(resId)
            val insReader = InputStreamReader(ins)
            val bufReader = BufferedReader(insReader)
            var line = bufReader.readLine()
            while (line != null) {
                body.append(line)
                body.append("\n")
                line = bufReader.readLine()
            }
        } catch (e: Exception) {
            Log.e("zjj", "exception $e")
        }
        return body.toString()
    }
}