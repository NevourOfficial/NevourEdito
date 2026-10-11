package com.nevour.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF0B0D10)
private val Surface = Color(0xFF14171C)
private val Raised = Color(0xFF1C2027)
private val Line = Color(0xFF2B3039)
private val Muted = Color(0xFF939BA8)
private val Ink = Color(0xFFF3F5F7)
private val Lime = Color(0xFFB7F36B)
private val LimeDark = Color(0xFF26351C)
private val Blue = Color(0xFF72C7FF)
private val Violet = Color(0xFF9D8CFF)

private enum class Page { Home, Editor, Projects, Library, Settings }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(primary = Lime, onPrimary = Bg, background = Bg, surface = Surface, onSurface = Ink, secondary = Violet)) {
                AppRoot()
            }
        }
    }
}

@Composable
private fun AppRoot() {
    var page by remember { mutableStateOf(Page.Home) }
    var project by remember { mutableStateOf("Weekend memories") }
    var message by remember { mutableStateOf("") }
    var exportDialog by remember { mutableStateOf(false) }

    Scaffold(containerColor = Bg, contentWindowInsets = WindowInsets(0, 0, 0, 0), bottomBar = {
        if (page != Page.Editor) {
            Row(Modifier.fillMaxWidth().background(Color(0xFF101216)).border(1.dp, Line.copy(alpha = .5f)).padding(horizontal = 6.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                NavButton("Home", Icons.Rounded.Home, page == Page.Home) { page = Page.Home }
                NavButton("Projects", Icons.Rounded.FolderOpen, page == Page.Projects) { page = Page.Projects }
                NavButton("Library", Icons.Rounded.VideoLibrary, page == Page.Library) { page = Page.Library }
                NavButton("Settings", Icons.Rounded.Settings, page == Page.Settings) { page = Page.Settings }
            }
        }
    }) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (page) {
                Page.Home -> HomePage(
                    onNew = { page = Page.Editor },
                    onProjects = { page = Page.Projects },
                    onLibrary = { page = Page.Library },
                    onOpen = { project = it; page = Page.Editor }
                )
                Page.Editor -> EditorPage(project, onBack = { page = Page.Home }, onSave = { message = "Save action previewed" }, onExport = { exportDialog = true })
                Page.Projects -> ProjectsPage(onOpen = { project = it; page = Page.Editor }, onNew = { page = Page.Editor })
                Page.Library -> LibraryPage()
                Page.Settings -> SettingsPage()
            }
            if (message.isNotBlank()) {
                Card(Modifier.align(Alignment.BottomCenter).padding(14.dp).clickable { message = "" }, colors = CardDefaults.cardColors(containerColor = Lime), shape = RoundedCornerShape(14.dp)) {
                    Text(message + "  ·  Tap to dismiss", color = Bg, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.padding(14.dp))
                }
            }
        }
    }
    if (exportDialog) ExportDialog(onDismiss = { exportDialog = false }, onConfirm = { exportDialog = false; message = "Export settings previewed" })
}

@Composable
private fun BrandMark() {
    Box(Modifier.size(43.dp).clip(RoundedCornerShape(13.dp)).background(Lime), contentAlignment = Alignment.Center) {
        Text("N", color = Bg, fontSize = 26.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun NavButton(label: String, icon: ImageVector, active: Boolean, onClick: () -> Unit) {
    Column(Modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick).padding(horizontal = 15.dp, vertical = 3.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Glyph(icon, if (active) Lime else Muted, 21)
        Text(label, color = if (active) Lime else Muted, fontSize = 10.sp, fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal)
    }
}

@Composable
private fun HomePage(onNew: () -> Unit, onProjects: () -> Unit, onLibrary: () -> Unit, onOpen: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp).padding(top = 22.dp, bottom = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BrandMark()
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text("NEVOUR", color = Ink, fontSize = 17.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                Text("EDITOR STUDIO", color = Muted, fontSize = 9.sp, letterSpacing = 2.sp)
            }
            Box(Modifier.size(42.dp).clip(CircleShape).background(Raised).clickable(onClick = onLibrary), contentAlignment = Alignment.Center) { Glyph(Icons.Rounded.AutoAwesome, Lime) }
        }
        Spacer(Modifier.height(30.dp))
        Text("Your next story", color = Ink, fontSize = 29.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
        Spacer(Modifier.height(6.dp))
        Text("Make something worth watching.", color = Muted, fontSize = 14.sp)
        Spacer(Modifier.height(22.dp))
        Card(Modifier.fillMaxWidth().clickable(onClick = onNew), colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(Color(0xFF283B2C), Color(0xFF18231F), Color(0xFF171A20)))).padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.clip(RoundedCornerShape(7.dp)).background(Lime).padding(horizontal = 9.dp, vertical = 5.dp)) { Text("NEW PROJECT", color = Bg, fontSize = 9.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp) }
                    Spacer(Modifier.weight(1f))
                    Glyph(Icons.Rounded.Add, Lime, 28)
                }
                Spacer(Modifier.height(34.dp))
                Text("Start with a blank", color = Ink, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(5.dp))
                Text("Bring in your clips. Build your cut.", color = Color(0xFFBEC8C0), fontSize = 13.sp)
                Spacer(Modifier.height(18.dp))
                Text("Create project  →", color = Lime, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(28.dp))
        SectionHeader("Continue editing", "See all", onProjects)
        Spacer(Modifier.height(13.dp))
        ProjectCard("Weekend memories", "Edited just now  •  00:42", listOf(Color(0xFF355C4B), Color(0xFF9C7558)), .68f) { onOpen("Weekend memories") }
        Spacer(Modifier.height(10.dp))
        ProjectCard("City after dark", "Yesterday  •  01:16", listOf(Color(0xFF292F57), Color(0xFF7D4B68)), .32f) { onOpen("City after dark") }
        Spacer(Modifier.height(27.dp))
        SectionHeader("Explore the library", "Browse", onLibrary)
        Spacer(Modifier.height(13.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FeatureCard("Looks & filters", "Give clips a mood", Icons.Rounded.Tune, Violet, Modifier.weight(1f), onLibrary)
            FeatureCard("Audio", "Find your sound", Icons.Rounded.LibraryMusic, Blue, Modifier.weight(1f), onLibrary)
        }
        Spacer(Modifier.height(16.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(18.dp)) {
            Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(LimeDark), contentAlignment = Alignment.Center) { Glyph(Icons.Rounded.SlowMotionVideo, Lime) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Made for your device", color = Ink, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Spacer(Modifier.height(3.dp))
                    Text("A lightweight-first editing experience.", color = Muted, fontSize = 11.sp)
                }
                Glyph(Icons.Rounded.ChevronRight, Muted)
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, action: String, onAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = Ink, fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(action, color = Lime, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable(onClick = onAction).padding(5.dp))
    }
}

@Composable
private fun ProjectCard(title: String, subtitle: String, colors: List<Color>, progress: Float, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(92.dp, 76.dp).clip(RoundedCornerShape(12.dp)).background(Brush.linearGradient(colors)), contentAlignment = Alignment.Center) {
                Box(Modifier.size(38.dp).clip(CircleShape).background(Color.White.copy(alpha = .14f)), contentAlignment = Alignment.Center) { Glyph(Icons.Rounded.PlayArrow, Color.White, 25) }
                Box(Modifier.align(Alignment.BottomStart).fillMaxWidth().height(4.dp).background(Color.Black.copy(alpha = .25f))) { Box(Modifier.fillMaxWidth(progress).height(4.dp).background(Lime)) }
            }
            Spacer(Modifier.width(13.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(5.dp))
                Text(subtitle, color = Muted, fontSize = 11.sp)
                Spacer(Modifier.height(9.dp))
                Text("Open project  →", color = Lime, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
            Glyph(Icons.Rounded.MoreHoriz, Muted)
        }
    }
}

@Composable
private fun FeatureCard(title: String, subtitle: String, icon: ImageVector, tint: Color, modifier: Modifier, onClick: () -> Unit) {
    Card(modifier.clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(15.dp)) {
            Box(Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(tint.copy(alpha = .14f)), contentAlignment = Alignment.Center) { Glyph(icon, tint) }
            Spacer(Modifier.height(17.dp))
            Text(title, color = Ink, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun EditorPage(project: String, onBack: () -> Unit, onSave: () -> Unit, onExport: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val player = remember { androidx.media3.exoplayer.ExoPlayer.Builder(context).build() }
    var importedVideos by remember { mutableStateOf<List<android.net.Uri>>(emptyList()) }
    var playing by remember { mutableStateOf(false) }
    var positionMs by remember { mutableLongStateOf(0L) }
    var durationMs by remember { mutableLongStateOf(0L) }
    var selectedTool by remember { mutableStateOf("Edit") }
    var showToolSheet by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("Select a tool or tap + to add media.") }
    val tools = listOf(
        Triple("Edit", Icons.Rounded.ContentCut, "Split and arrange clips"),
        Triple("Audio", Icons.Rounded.MusicNote, "Add an audio track"),
        Triple("Text", Icons.Rounded.TextFields, "Add a text layer"),
        Triple("Effects", Icons.Rounded.AutoAwesome, "Effects and looks"),
        Triple("Overlay", Icons.Rounded.Layers, "Add an overlay"),
        Triple("Captions", Icons.Rounded.ClosedCaption, "Caption tools")
    )
    val pickVideos = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach { uri ->
                runCatching { context.contentResolver.takePersistableUriPermission(uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION) }
            }
            importedVideos = importedVideos + uris
            uris.forEach { uri -> player.addMediaItem(androidx.media3.common.MediaItem.fromUri(uri)) }
            player.prepare()
            status = "Added ${uris.size} video clip(s) to the timeline."
        }
    }
    DisposableEffect(player) {
        val listener = object : androidx.media3.common.Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) { playing = isPlaying }
            override fun onPlaybackStateChanged(playbackState: Int) { durationMs = player.duration.coerceAtLeast(0L) }
        }
        player.addListener(listener)
        onDispose { player.removeListener(listener); player.release() }
    }
    LaunchedEffect(player, playing) {
        while (playing) {
            positionMs = player.currentPosition.coerceAtLeast(0L)
            durationMs = player.duration.coerceAtLeast(0L)
            kotlinx.coroutines.delay(250)
        }
    }

    Column(Modifier.fillMaxSize().background(Color(0xFF0B0C0D))) {
        // Approved prototype header: close, project title, settings, export.
        Row(
            Modifier.fillMaxWidth().background(Color(0xFF111213)).padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) { Glyph(Icons.Rounded.Close, Ink, 22) }
            Column(Modifier.weight(1f)) {
                Text("NEVOUR", color = Muted, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                Text(project, color = Ink, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            IconButton(onClick = onSave, modifier = Modifier.size(40.dp)) { Glyph(Icons.Rounded.Settings, Ink, 22) }
            IconButton(onClick = onExport, modifier = Modifier.size(40.dp)) { Glyph(Icons.Rounded.FileUpload, Lime, 25) }
        }

        // The preview remains completely unobstructed: no playback overlay or player controls.
        Box(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 5.dp)
                .aspectRatio(1.35f).clip(RoundedCornerShape(3.dp)).background(Color(0xFF272C30)),
            contentAlignment = Alignment.Center
        ) {
            if (importedVideos.isNotEmpty()) {
                androidx.compose.ui.viewinterop.AndroidView(
                    factory = { ctx ->
                        androidx.media3.ui.PlayerView(ctx).apply {
                            this.player = player
                            useController = false
                            resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
                            setShutterBackgroundColor(android.graphics.Color.BLACK)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Glyph(Icons.Rounded.VideoLibrary, Color(0xFF9CA6A2), 42)
                    Spacer(Modifier.height(8.dp))
                    Text("Your video preview", color = Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text("Tap + to add video clips", color = Color(0xFFB8BABD), fontSize = 11.sp)
                }
            }
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(formatTime(positionMs), color = Muted, fontSize = 11.sp)
            Spacer(Modifier.weight(1f))
            Text("16:9  ·  1080p", color = Muted, fontSize = 11.sp)
        }

        // Transport row and history controls stay above the timeline, as approved.
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { status = "Preview fit selected." }, modifier = Modifier.size(38.dp)) { Glyph(Icons.Rounded.Fullscreen, Muted, 21) }
            Spacer(Modifier.width(4.dp))
            Text("${formatTime(positionMs)} / ${formatTime(durationMs)}", color = Muted, fontSize = 10.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { player.seekTo((player.currentPosition - 5000L).coerceAtLeast(0L)) }, modifier = Modifier.size(36.dp)) { Glyph(Icons.Rounded.Replay5, Ink, 21) }
            IconButton(
                onClick = { if (player.isPlaying) player.pause() else if (player.mediaItemCount > 0) player.play() },
                modifier = Modifier.size(38.dp).clip(CircleShape).background(Lime)
            ) { Glyph(if (playing) Icons.Rounded.Pause else Icons.Rounded.PlayArrow, Bg, 24) }
            IconButton(onClick = { player.seekTo(player.currentPosition + 5000L) }, modifier = Modifier.size(36.dp)) { Glyph(Icons.Rounded.Forward5, Ink, 21) }
        }

        Divider(color = Color(0xFF292A2C))
        Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("TIMELINE", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { status = "Undo: no edit operation to reverse yet." }, contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)) {
                Text("↶ Undo", color = Muted, fontSize = 11.sp)
            }
            TextButton(onClick = { status = "Redo: no edit operation to restore yet." }, contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)) {
                Text("↷ Redo", color = Muted, fontSize = 11.sp)
            }
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("00:00", "00:06", "00:12", "00:18", "00:24").forEach {
                Text(it, color = Muted, fontSize = 9.sp)
            }
        }
        Divider(Modifier.padding(horizontal = 14.dp, vertical = 3.dp), color = Line)
        Row(Modifier.fillMaxWidth().heightIn(min = 68.dp).padding(horizontal = 10.dp, vertical = 5.dp), verticalAlignment = Alignment.Top) {
            Text("Video", color = Muted, fontSize = 9.sp, modifier = Modifier.width(42.dp).padding(top = 13.dp))
            Row(Modifier.weight(1f).height(57.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFF202224)).horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.CenterVertically) {
                importedVideos.forEachIndexed { index, uri ->
                    Column(
                        Modifier.width(83.dp).fillMaxHeight().padding(end = 2.dp)
                            .background(if (player.currentMediaItemIndex == index) Color(0xFF35422C) else Color(0xFF343B3C))
                            .clickable { player.seekTo(index, 0L); player.prepare() }.padding(5.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Glyph(Icons.Rounded.Movie, if (player.currentMediaItemIndex == index) Lime else Color(0xFFBFC1C4), 17)
                        Text(uri.lastPathSegment?.takeLast(12) ?: "Clip ${index + 1}", color = Ink, fontSize = 8.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
                Box(
                    Modifier.width(42.dp).fillMaxHeight().background(Color(0xFF292B2D)).border(width = 1.dp, color = Lime.copy(alpha = .7f))
                        .clickable { pickVideos.launch(arrayOf("video/*", "image/*")) },
                    contentAlignment = Alignment.Center
                ) { Glyph(Icons.Rounded.Add, Lime, 28) }
            }
        }
        Row(Modifier.fillMaxWidth().padding(start = 10.dp, end = 14.dp, top = 2.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Audio", color = Muted, fontSize = 9.sp, modifier = Modifier.width(52.dp))
            Row(Modifier.weight(1f).height(27.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFF222426)), horizontalArrangement = Arrangement.spacedBy(3.dp), verticalAlignment = Alignment.CenterVertically) {
                repeat(74) { i -> Box(Modifier.width(2.dp).height((4 + (kotlin.math.abs(kotlin.math.sin(i * 1.7)) * 17)).dp).clip(CircleShape).background(Color(0xFF7E9C5C))) }
            }
        }
        Row(Modifier.fillMaxWidth().padding(start = 10.dp, end = 14.dp, top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Text", color = Muted, fontSize = 9.sp, modifier = Modifier.width(52.dp))
            Box(Modifier.weight(1f).height(24.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFF202224)).clickable { selectedTool = "Text"; showToolSheet = true; status = "Text layer tools selected." }.padding(horizontal = 8.dp), contentAlignment = Alignment.CenterStart) {
                Text("+ Add text", color = Color(0xFF777B80), fontSize = 10.sp)
            }
        }

        Spacer(Modifier.weight(1f, fill = true))
        Row(Modifier.fillMaxWidth().background(Color(0xFF111213)).border(width = 1.dp, color = Color(0xFF292A2C)).padding(horizontal = 3.dp, vertical = 7.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            tools.forEach { (label, icon, _) ->
                Column(
                    Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).clickable {
                        selectedTool = label
                        showToolSheet = true
                        status = "$label tools selected."
                    }.padding(vertical = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Glyph(icon, if (selectedTool == label) Lime else Color(0xFFBFC1C4), 22)
                    Text(label, color = if (selectedTool == label) Lime else Color(0xFFBFC1C4), fontSize = 9.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                }
            }
        }
        Row(Modifier.fillMaxWidth().background(Color(0xFF191A1B)).padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(status, color = Color(0xFFA5A7A9), fontSize = 10.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        if (showToolSheet) {
            Surface(color = Color(0xFF1B1C1D), tonalElevation = 4.dp) {
                Column(Modifier.fillMaxWidth().padding(14.dp)) {
                    Text("$selectedTool tools", color = Ink, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(tools.firstOrNull { it.first == selectedTool }?.third ?: "", color = Muted, fontSize = 11.sp)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { if (selectedTool == "Audio") status = "Audio import is the next connected workflow."; showToolSheet = false }) {
                            Text("Done", color = Lime)
                        }
                    }
                }
            }
        }
    }
}

private fun formatTime(milliseconds: Long): String {
    val totalSeconds = (milliseconds / 1000L).coerceAtLeast(0L)
    return "%02d:%02d.%d".format(totalSeconds / 60, totalSeconds % 60, (milliseconds % 1000L) / 100L)
}

@Composable
private fun ProjectsPage(onOpen: (String) -> Unit, onNew: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp).padding(top = 20.dp, bottom = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Your projects", color = Ink, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Text("Local drafts and recent edits", color = Muted, fontSize = 13.sp)
            }
            Box(Modifier.size(42.dp).clip(RoundedCornerShape(13.dp)).background(Lime).clickable(onClick = onNew), contentAlignment = Alignment.Center) { Glyph(Icons.Rounded.Add, Bg, 24) }
        }
        Spacer(Modifier.height(22.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Search projects", color = Muted, fontSize = 13.sp) }, leadingIcon = { Glyph(Icons.Rounded.Search, Muted) }, shape = RoundedCornerShape(14.dp), singleLine = true, readOnly = true)
        Spacer(Modifier.height(16.dp))
        ProjectCard("Weekend memories", "Edited just now  •  00:42", listOf(Color(0xFF355C4B), Color(0xFF9C7558)), .68f) { onOpen("Weekend memories") }
        Spacer(Modifier.height(12.dp))
        ProjectCard("City after dark", "Yesterday  •  01:16", listOf(Color(0xFF292F57), Color(0xFF7D4B68)), .32f) { onOpen("City after dark") }
        Spacer(Modifier.height(12.dp))
        ProjectCard("Product teaser", "3 days ago  •  00:18", listOf(Color(0xFF4E5B30), Color(0xFF6E7D93)), .8f) { onOpen("Product teaser") }
        Spacer(Modifier.height(24.dp))
        InfoCard("Projects stay on your device", "This UI prototype uses sample projects. Import, storage, and persistence will be connected later.")
    }
}

@Composable
private fun LibraryPage() {
    var category by remember { mutableStateOf("Looks") }
    var selected by remember { mutableStateOf("") }
    val categories = listOf("Looks", "Transitions", "Audio", "Titles", "Stickers")
    val assets = when (category) {
        "Looks" -> listOf("Soft film", "Golden hour", "Night drive", "Clean neutral", "Vintage fade", "Cool shadows")
        "Transitions" -> listOf("Smooth zoom", "Light leak", "Swipe left", "Blur dissolve")
        "Audio" -> listOf("Ambient pulse", "Lo-fi morning", "Cinematic rise", "Soft piano")
        "Titles" -> listOf("Minimal title", "Bold intro", "Lower third", "Subtitle clean")
        else -> listOf("Shapes pack", "Travel icons", "Hand-drawn marks", "Retro labels")
    }
    val colors = listOf(Violet, Blue, Lime, Color(0xFFFFB56B), Color(0xFFEF8AAE), Color(0xFF83D6C3))
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp).padding(top = 20.dp, bottom = 24.dp)) {
        Text("Asset library", color = Ink, fontSize = 27.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text("Optional extras, only when you want them.", color = Muted, fontSize = 13.sp)
        Spacer(Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) { categories.forEach { FilterPill(it, category == it) { category = it; selected = "" } } }
        Spacer(Modifier.height(18.dp))
        Card(colors = CardDefaults.cardColors(containerColor = LimeDark), shape = RoundedCornerShape(16.dp)) {
            Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                Glyph(Icons.Rounded.Download, Lime)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text("Download on demand", color = Ink, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("No asset packs download on launch.", color = Color(0xFFC1D5B1), fontSize = 11.sp)
                }
            }
        }
        Spacer(Modifier.height(19.dp))
        Text(category + " for your next edit", color = Ink, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        assets.chunked(2).forEachIndexed { rowIndex, row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEachIndexed { colIndex, asset ->
                    val i = rowIndex * 2 + colIndex
                    Card(Modifier.weight(1f).clickable { selected = if (selected == asset) "" else asset }, colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(16.dp)) {
                        Column(Modifier.padding(10.dp)) {
                            Box(Modifier.fillMaxWidth().height(105.dp).clip(RoundedCornerShape(11.dp)).background(Brush.linearGradient(listOf(colors[i % colors.size].copy(alpha = .8f), Raised))), contentAlignment = Alignment.Center) {
                                Glyph(if (selected == asset) Icons.Rounded.Check else when (category) {
                                    "Audio" -> Icons.Rounded.MusicNote
                                    "Titles" -> Icons.Rounded.TextFields
                                    "Looks" -> Icons.Rounded.Tune
                                    else -> Icons.Rounded.AutoAwesome
                                }, Color.White, 28)
                                Text("PREVIEW", color = Color.White.copy(alpha = .75f), fontSize = 8.sp, letterSpacing = 1.sp, modifier = Modifier.align(Alignment.BottomStart).padding(8.dp))
                            }
                            Spacer(Modifier.height(10.dp))
                            Text(asset, color = Ink, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(4.dp))
                            Text(if (selected == asset) "Selected for preview" else "Tap to preview", color = if (selected == asset) Lime else Muted, fontSize = 10.sp)
                        }
                    }
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
        }
        Text("Sample catalog only. Downloading and applying assets is not connected yet.", color = Muted, fontSize = 11.sp, lineHeight = 16.sp)
    }
}

@Composable
private fun SettingsPage() {
    var adaptive by remember { mutableStateOf(true) }
    var haptics by remember { mutableStateOf(true) }
    var awake by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp).padding(top = 20.dp, bottom = 24.dp)) {
        Text("Settings", color = Ink, fontSize = 27.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text("Shape the editor around your workflow.", color = Muted, fontSize = 13.sp)
        Spacer(Modifier.height(22.dp))
        Text("PLAYBACK & PERFORMANCE", color = Muted, fontSize = 10.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        ToggleRow("Adaptive preview", "Lower preview quality under load", adaptive) { adaptive = it }
        ToggleRow("Haptic feedback", "Small feedback on key actions", haptics) { haptics = it }
        ToggleRow("Keep screen awake", "While the editor is open", awake) { awake = it }
        Spacer(Modifier.height(20.dp))
        Text("WORKSPACE", color = Muted, fontSize = 10.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        SettingsRow("Default export", "1080p · 30 FPS", Icons.Rounded.VideoLibrary)
        SettingsRow("Asset cache", "Manage downloaded extras", Icons.Rounded.Download)
        SettingsRow("Enabled modules", "Basic editing, audio, text", Icons.Rounded.Layers)
        Spacer(Modifier.height(20.dp))
        Text("ABOUT", color = Muted, fontSize = 10.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        SettingsRow("Nevour Editor", "UI prototype · version 0.1.0", Icons.Rounded.AutoAwesome)
        SettingsRow("Open-source licenses", "Code and asset licenses", Icons.Rounded.Share)
        Spacer(Modifier.height(15.dp))
        OutlinedButton(onClick = { notes = !notes }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(13.dp)) { Text(if (notes) "Hide prototype notes" else "Show prototype notes", color = Ink) }
        if (notes) {
            Spacer(Modifier.height(10.dp))
            InfoCard("Prototype settings", "Settings are interactive demo state only. Preferences, cache controls, module installation, and export defaults are not persisted or connected yet.")
        }
    }
}

@Composable
private fun ToggleRow(title: String, description: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(title, color = Ink, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Text(description, color = Muted, fontSize = 11.sp)
        }
        Switch(checked = checked, onCheckedChange = onChange)
    }
    Divider(color = Line.copy(alpha = .7f))
}

@Composable
private fun SettingsRow(title: String, description: String, icon: ImageVector) {
    Column {
        Row(Modifier.fillMaxWidth().clickable { }.padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(39.dp).clip(RoundedCornerShape(12.dp)).background(Raised), contentAlignment = Alignment.Center) { Glyph(icon, Muted, 19) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = Ink, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(3.dp))
                Text(description, color = Muted, fontSize = 11.sp)
            }
            Glyph(Icons.Rounded.ChevronRight, Muted, 19)
        }
        Divider(color = Line.copy(alpha = .7f))
    }
}

@Composable
private fun InfoCard(title: String, description: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(title, color = Ink, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(5.dp))
            Text(description, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
        }
    }
}

@Composable
private fun ExportDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    var resolution by remember { mutableStateOf("1080p") }
    var fps by remember { mutableStateOf("30 FPS") }
    var quality by remember { mutableFloatStateOf(.8f) }
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Surface,
        title = { Text("Export settings", color = Ink, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Resolution", color = Muted, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) { listOf("720p", "1080p", "4K").forEach { FilterPill(it, resolution == it) { resolution = it } } }
                Spacer(Modifier.height(16.dp))
                Text("Frame rate", color = Muted, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) { listOf("24 FPS", "30 FPS", "60 FPS").forEach { FilterPill(it, fps == it) { fps = it } } }
                Spacer(Modifier.height(16.dp))
                Text("Quality  " + (quality * 100).toInt() + "%", color = Muted, fontSize = 12.sp)
                Slider(value = quality, onValueChange = { quality = it }, colors = SliderDefaults.colors(thumbColor = Lime, activeTrackColor = Lime))
                Text("UI preview only. Video encoding is not connected yet.", color = Muted, fontSize = 11.sp, lineHeight = 16.sp)
            }
        },
        confirmButton = { Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(containerColor = Lime, contentColor = Bg)) { Text("Confirm settings") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = Muted) } }
    )
}

@Composable
private fun Glyph(icon: ImageVector, tint: Color = Ink, size: Int = 20) {
    Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(size.dp))
}

@Composable
private fun FilterPill(label: String, active: Boolean, onClick: () -> Unit) {
    Box(Modifier.clip(RoundedCornerShape(10.dp)).background(if (active) LimeDark else Surface).border(1.dp, if (active) Lime.copy(alpha = .65f) else Line, RoundedCornerShape(10.dp)).clickable(onClick = onClick).padding(horizontal = 13.dp, vertical = 9.dp)) {
        Text(label, color = if (active) Lime else Ink, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ToolButton(label: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    Column(Modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick).padding(horizontal = 11.dp, vertical = 5.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(if (selected) LimeDark else Surface), contentAlignment = Alignment.Center) { Glyph(icon, if (selected) Lime else Muted, 20) }
        Text(label, color = if (selected) Lime else Muted, fontSize = 10.sp)
    }
}
