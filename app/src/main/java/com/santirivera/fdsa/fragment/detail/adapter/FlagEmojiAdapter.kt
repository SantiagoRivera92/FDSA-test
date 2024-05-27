import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.santirivera.fdsa.R
import com.santirivera.fdsa.utils.toFlagEmoji

class FlagEmojiAdapter(
    context: Context,
    private val objects: List<String>
) : ArrayAdapter<String>(context, R.layout.destination_spinner_item, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    private fun createViewFromResource(
        convertView: View?,
        parent: ViewGroup,
        position: Int
    ): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.destination_spinner_item, parent, false)
        val textView: TextView = view.findViewById(R.id.spinnerItemTextView)

        val countryCode = getItem(position)
        val flagEmoji = countryCode?.toFlagEmoji()
        val spannableString = createSpannableString(countryCode, flagEmoji)

        textView.text = spannableString

        return view
    }

    private fun createSpannableString(countryCode: String?, flagEmoji: String?): SpannableString {
        val text = "$countryCode $flagEmoji"
        val spannableString = SpannableString(text)

        countryCode?.let {
            spannableString.setSpan(
                RelativeSizeSpan(0.75f),
                0,
                countryCode.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }
}