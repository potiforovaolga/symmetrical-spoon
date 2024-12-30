
package com.example.myapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import com.prof.rssparser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.myapi.ui.theme.MyApiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApiTheme {
                RSSReaderApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RSSReaderApp() {
    var newsItems by remember { mutableStateOf<List<com.prof.rssparser.Article>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val rssFeedUrl = "https://lenta.ru/rss/" 
    val coroutineScope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CenterAlignedTopAppBar(title = { Text("RSS Reader") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        try {
                            val parser = Parser()
                            val channel = withContext(Dispatchers.IO) {
                                parser.getChannel(rssFeedUrl)
                            }
                            newsItems = channel.articles.take(10)
                        } catch (e: Exception) {
                            Log.e("RSS Reader", "Error fetching RSS: ${e.message}")
                            snackbarHostState.showSnackbar("Error: ${e.message.orEmpty()}")
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading
            ) {
                Text("Get News")
            }

            if (isLoading) {
                CircularProgressIndicator()
            }

            newsItems?.let { items ->
                LazyColumn {
                    items(items) { item ->
                        NewsItem(item, uriHandler)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(item: com.prof.rssparser.Article, uriHandler: UriHandler) {
    Column(modifier = Modifier
        .clickable { item.link?.let { uriHandler.openUri(it) } }
        .padding(16.dp)
    ) {
        Text(item.title ?: "", style = MaterialTheme.typography.headlineSmall)
        Text(item.pubDate?.toString() ?: "", style = MaterialTheme.typography.bodySmall)
    }
    Divider() // Заменено на Divider для соответствия Material3
}
