import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val uri = chain.request().url().uri().toString()
        val responseBodyString = when {
            uri.endsWith("GetAll") -> getJsonFromAssets(context, "get_all.json")
            uri.endsWith("DeleteById") -> getJsonFromAssets(context, "delete_by_id.json")
            uri.endsWith("Create") -> getJsonFromAssets(context, "create.json")
            uri.endsWith("Update") -> getJsonFromAssets(context, "update.json")
            else -> throw IOException("Unexpected request URL: $uri")
        }

        val responseBody = ResponseBody.create(MediaType.get("application/json"), responseBodyString)

        return Response.Builder()
            .code(200)
            .message("Mock response")
            .body(responseBody)
            .request(chain.request())
            .protocol(Protocol.HTTP_2)
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun getJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
