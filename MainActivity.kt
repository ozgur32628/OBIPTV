package com.obiptv.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ObIptvApp() }
    }
}

private val Bg = Color(0xFF080A10)
private val Purple = Color(0xFFA900F5)
private val Red = Color(0xFFF33F4B)
private val Cyan = Color(0xFF42A6BB)
private val Green = Color(0xFF27B77D)
private val DeepPurple = Color(0xFF6517D6)

@Composable
fun ObIptvApp() {
    var selected by remember { mutableStateOf<String?>(null) }
    if (selected == null) HomeScreen { selected = it }
    else PlaceholderScreen(selected!!) { selected = null }
}

@Composable
private fun HomeScreen(onOpen: (String) -> Unit) {
    var now by remember { mutableStateOf(Date()) }
    LaunchedEffect(Unit) {
        while (true) { now = Date(); kotlinx.coroutines.delay(1000) }
    }
    val time = remember(now) { SimpleDateFormat("HH:mm", Locale.getDefault()).format(now) }
    val date = remember(now) { SimpleDateFormat("d MMMM yyyy", Locale("tr", "TR")).format(now) }

    Box(Modifier.fillMaxSize().background(Bg).padding(10.dp)) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth().height(52.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(42.dp).background(
                        Brush.linearGradient(listOf(Purple, Red)), RoundedCornerShape(10.dp)
                    ), contentAlignment = Alignment.Center
                ) { Text("OB", color = Color.White, fontWeight = FontWeight.Black, fontSize = 15.sp) }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(time, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(date, color = Color(0xFFB8BBC5), fontSize = 10.sp)
                }
                Spacer(Modifier.weight(1f))
                SmallButton(Icons.Default.Settings) { onOpen("SETTINGS") }
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MenuCard("LIVE TV", "7.044", Icons.Default.LiveTv, Purple, Modifier.weight(1.05f).fillMaxHeight()) { onOpen("LIVE TV") }
                Column(Modifier.weight(1.9f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MenuCard("MOVIES", "10.772", Icons.Default.Movie, Red, Modifier.weight(1f).fillMaxHeight()) { onOpen("MOVIES") }
                        MenuCard("SERIES", "1.369", Icons.Default.VideoLibrary, Cyan, Modifier.weight(1f).fillMaxHeight()) { onOpen("SERIES") }
                    }
                    Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MenuCard("MULTISCREEN", "", Icons.Default.DesktopWindows, Green, Modifier.weight(1f).fillMaxHeight()) { onOpen("MULTISCREEN") }
                        MenuCard("CONTINUE WATCHING", "CATCH UP", Icons.Default.History, DeepPurple, Modifier.weight(1f).fillMaxHeight()) { onOpen("CONTINUE WATCHING") }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuCard(title: String, subtitle: String, icon: ImageVector, color: Color, modifier: Modifier, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(
        modifier
            .onFocusChanged { focused = it.isFocused }
            .border(if (focused) 4.dp else 0.dp, Color.White, RoundedCornerShape(9.dp))
            .background(color, RoundedCornerShape(9.dp))
            .clickable { onClick() }
            .focusable(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(34.dp))
            Spacer(Modifier.height(8.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            if (subtitle.isNotBlank()) {
                Spacer(Modifier.height(3.dp))
                Surface(color = Color.Black.copy(alpha = .22f), shape = RoundedCornerShape(8.dp)) {
                    Text(subtitle, color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
        }
    }
}

@Composable
private fun SmallButton(icon: ImageVector, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    Box(
        Modifier.size(42.dp).onFocusChanged { focused = it.isFocused }
            .border(if (focused) 3.dp else 1.dp, if (focused) Color.White else Color(0xFF363A44), RoundedCornerShape(9.dp))
            .background(Color(0xFF1C1F27), RoundedCornerShape(9.dp)).clickable { onClick() }.focusable(),
        contentAlignment = Alignment.Center
    ) { Icon(icon, null, tint = Color.White) }
}

@Composable
private fun PlaceholderScreen(title: String, onBack: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Bg).padding(28.dp)) {
        Column {
            Text("‹  GERİ", color = Color(0xFFB98BFF), fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onBack() }.padding(10.dp))
            Spacer(Modifier.height(35.dp))
            Text(title, color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(12.dp))
            Text("OBIPTV v1 • Bu bölüm bir sonraki aşamada içerik kaynağı ve oynatıcı ile bağlanacak.", color = Color(0xFFB8BBC5), fontSize = 16.sp)
        }
    }
}
