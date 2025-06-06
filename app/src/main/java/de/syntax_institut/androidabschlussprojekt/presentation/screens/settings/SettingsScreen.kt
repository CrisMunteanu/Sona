package de.syntax_institut.androidabschlussprojekt.presentation.screens.settings

import android.app.Activity
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.SettingsDataStore
import de.syntax_institut.androidabschlussprojekt.domain.util.NotificationHelper
import de.syntax_institut.androidabschlussprojekt.domain.util.setLocaleAndRestart
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn


@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedLanguageCode by remember { mutableStateOf("de") }
    var isReminderEnabled by remember { mutableStateOf(false) }
    var reminderHour by remember { mutableStateOf(8) }
    var reminderMinute by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        selectedLanguageCode = SettingsDataStore.getLanguageCode(context)
        isReminderEnabled = SettingsDataStore.getReminderEnabled(context)
        reminderHour = SettingsDataStore.getReminderHour(context)
        reminderMinute = SettingsDataStore.getReminderMinute(context)
    }

    val emojiLanguages = listOf(
        "🇩🇪" to "de",
        "🇬🇧" to "en",
        "🇫🇷" to "fr",
        "🇪🇸" to "es"
    )

    val timeText = String.format(
        "%02d:%02d", reminderHour, reminderMinute)

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                reminderHour = hour
                reminderMinute = minute
                scope.launch {
                    SettingsDataStore.saveReminderTime(context, hour, minute)
                }
                if (isReminderEnabled) {
                    NotificationHelper.scheduleDailyReminder(context, hour, minute)
                    scope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.reminder_set_for, timeText))
                    }
                }
            },
            reminderHour,
            reminderMinute,
            true
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                    color = ElegantRed
                )
            }

            item {
                Text(stringResource(R.string.language), fontSize = 18.sp)
                var expanded by remember { mutableStateOf(false) }

                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val currentEmoji = emojiLanguages.firstOrNull {
                            it.second == selectedLanguageCode }?.first ?: "🌐"
                        Text("$currentEmoji (${selectedLanguageCode.uppercase()})")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        emojiLanguages.forEach { (emoji, code) ->
                            DropdownMenuItem(
                                text = { Text("$emoji  ${code.uppercase()}") },
                                onClick = {
                                    expanded = false
                                    scope.launch {
                                        SettingsDataStore.saveLanguageCode(context, code)
                                        activity?.let { setLocaleAndRestart(it, code) }
                                    }
                                }
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.dark_mode), fontSize = 18.sp)
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = onToggleDarkMode
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        scope.launch {
                            SettingsDataStore.setOnboardingSeen(context, false)
                            snackbarHostState.showSnackbar(context.getString(R.string.onboarding_reset_success))
                            activity?.recreate()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.reset_onboarding))
                }
            }

            item {
                Text(stringResource(R.string.reminder_title), fontSize = 18.sp, color = ElegantRed)

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stringResource(R.string.reminder_switch_label))
                        Switch(
                            checked = isReminderEnabled,
                            onCheckedChange = { isChecked ->
                                isReminderEnabled = isChecked
                                scope.launch {
                                    SettingsDataStore.saveReminderEnabled(context, isChecked)

                                    if (isChecked) {
                                        NotificationHelper.scheduleDailyReminder(context, reminderHour, reminderMinute)
                                        snackbarHostState.showSnackbar(
                                            context.getString(
                                                R.string.reminder_enabled_snackbar,
                                                String.format("%02d:%02d", reminderHour, reminderMinute)
                                            )
                                        )
                                    } else {
                                        NotificationHelper.cancelDailyReminder(context)
                                        snackbarHostState.showSnackbar(context.getString
                                            (R.string.reminder_disabled_snackbar))
                                    }
                                }
                            }
                        )
                    }

                    if (isReminderEnabled) {
                        Text(
                            text = stringResource(
                                R.string.reminder_active_since,
                                String.format
                                    ("%02d:%02d", reminderHour, reminderMinute)
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.75f),
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.reminder_time_label))
                    OutlinedButton(onClick = { timePickerDialog.show() }) {
                        Text(timeText)
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        NotificationHelper.showReminderNotification(
                            context = context,
                            title = context.getString(R.string.reminder_title),
                            message = context.getString(R.string.reminder_test_message)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("🔔 " + stringResource(R.string.send_test_notification))
                }
            }

            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_sona),
                        contentDescription = "Sona Logo",
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}