package tokyo.punchdrunker.hocho

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.android.material.snackbar.Snackbar
import tokyo.punchdrunker.hocho.databinding.ActivityLayoutBinding

class LayoutActivity : AppCompatActivity() {
    private val binding: ActivityLayoutBinding by lazy {
        DataBindingUtil.setContentView<ActivityLayoutBinding>(this, R.layout.activity_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        binding.componentInclude.button.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.snackbar_message), Snackbar.LENGTH_SHORT).show()
        }
        binding.user = User("punchdrunker", "engineer")
        binding.fab.setOnClickListener {
            Snackbar.make(it, getString(R.string.fab_message), Snackbar.LENGTH_SHORT).show()
        }
        binding.greeting.setContent {
            MdcTheme {
                Greeting()
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = ""
        }
        binding.toolbarTitle.text = getString(R.string.layout_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var result = true

        when (id) {
            android.R.id.home -> finish()
            else -> result = super.onOptionsItemSelected(item)
        }

        return result
    }

    data class User constructor(val name: String, val title: String)
}

@Composable
private fun Greeting() {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            text = stringResource(R.string.greeting),
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.margin_small))
        )
        Text(text = "Wonderful tonight", style = MaterialTheme.typography.h3)
        Text(text = "tonight", style = MaterialTheme.typography.h3)
    }
}

@Preview
@Composable
private fun preview() {
    Greeting()
}