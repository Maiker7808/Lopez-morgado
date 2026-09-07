package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.AgentBotViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialMediaScreen(viewModel: AgentBotViewModel) {
    var pageIdInput by remember { mutableStateOf("1234567890") }
    var accessTokenInput by remember { mutableStateOf("") }
    var fbPostContent by remember { mutableStateOf("") }
    var fbLinkInput by remember { mutableStateOf("") }

    var ytVideoTitle by remember { mutableStateOf("") }
    var ytTokenInput by remember { mutableStateOf("") }
    var ytVideoIdInput by remember { mutableStateOf("") }
    var ytCommentInput by remember { mutableStateOf("") }

    var twilioSidInput by remember { mutableStateOf("") }
    var twilioTokenInput by remember { mutableStateOf("") }
    var twilioFromInput by remember { mutableStateOf("whatsapp:+14155238886") }
    var twilioToInput by remember { mutableStateOf("") }
    var twilioMessageInput by remember { mutableStateOf("¡Tarea ejecutada con éxito por CrewAI Bot!") }

    var publishStatus by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Social Media Module",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Facebook Meta Graph API & YouTube Data API v3",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Facebook Meta Graph API Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Public, contentDescription = "Facebook", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Facebook Page (Meta Graph API)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Endpoint: /{page_id}/feed | Auth: Page Access Token (pages_manage_posts, pages_read_engagement)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = pageIdInput,
                    onValueChange = { pageIdInput = it },
                    label = { Text("FACEBOOK_PAGE_ID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = accessTokenInput,
                    onValueChange = { accessTokenInput = it },
                    label = { Text("FACEBOOK_PAGE_ACCESS_TOKEN (EAA...)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = fbPostContent,
                    onValueChange = { fbPostContent = it },
                    label = { Text("Post Message (mensaje)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = fbLinkInput,
                    onValueChange = { fbLinkInput = it },
                    label = { Text("Optional Link (link preview URL)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (fbPostContent.isNotBlank()) {
                            publishStatus = "Enviando post a Meta Graph API..."
                            viewModel.publishToFacebook(
                                pageId = pageIdInput,
                                accessToken = accessTokenInput,
                                message = fbPostContent,
                                link = fbLinkInput.ifBlank { null }
                            ) { result ->
                                publishStatus = result
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Publish")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Publicar en Facebook (/{page_id}/feed)")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // YouTube Data API v3 Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PlayCircle, contentDescription = "YouTube", tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "YouTube Channel (Data API v3)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Auth: OAuth 2.0 (videos.insert, comment moderation)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = ytTokenInput,
                    onValueChange = { ytTokenInput = it },
                    label = { Text("YouTube OAuth 2.0 Access Token (Bearer)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = ytVideoIdInput,
                    onValueChange = { ytVideoIdInput = it },
                    label = { Text("Video ID (ej. dQw4w9WgXcQ)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = ytCommentInput,
                    onValueChange = { ytCommentInput = it },
                    label = { Text("Comentario (textOriginal)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (ytVideoIdInput.isNotBlank() && ytCommentInput.isNotBlank()) {
                            publishStatus = "Publicando comentario en YouTube (commentThreads)..."
                            viewModel.publishToYouTubeComment(
                                accessToken = ytTokenInput,
                                videoId = ytVideoIdInput,
                                commentText = ytCommentInput
                            ) { result ->
                                publishStatus = result
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Comment, contentDescription = "Comment")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Publicar Comentario en YouTube (commentThreads)")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Twilio WhatsApp Alert Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = MaterialTheme.colorScheme.tertiary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Twilio WhatsApp Alerts", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "API: Twilio Messaging / Accounts/{account_sid}/Messages.json", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = twilioSidInput,
                    onValueChange = { twilioSidInput = it },
                    label = { Text("TWILIO_ACCOUNT_SID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = twilioTokenInput,
                    onValueChange = { twilioTokenInput = it },
                    label = { Text("TWILIO_AUTH_TOKEN") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = twilioToInput,
                    onValueChange = { twilioToInput = it },
                    label = { Text("MI_NUMERO_WHATSAPP (ej: whatsapp:+5939...)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = twilioMessageInput,
                    onValueChange = { twilioMessageInput = it },
                    label = { Text("Mensaje (resumen_tarea)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (twilioSidInput.isNotBlank() && twilioTokenInput.isNotBlank() && twilioToInput.isNotBlank()) {
                            publishStatus = "Enviando alerta de WhatsApp vía Twilio..."
                            viewModel.sendTwilioWhatsApp(
                                accountSid = twilioSidInput,
                                authToken = twilioTokenInput,
                                fromNumber = twilioFromInput,
                                toNumber = twilioToInput,
                                messageText = twilioMessageInput
                            ) { result ->
                                publishStatus = result
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send WhatsApp")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Enviar Alerta de WhatsApp (Twilio)")
                }
            }
        }

        if (publishStatus.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = publishStatus,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
