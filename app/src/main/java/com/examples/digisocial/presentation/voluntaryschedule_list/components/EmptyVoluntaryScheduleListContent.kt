package com.examples.digisocial.presentation.voluntaryschedule_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.examples.digisocial.R

@Composable
fun EmptyVoluntaryScheduleListContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = stringResource(
                id = R.string.empty_voluntary_list_text
            ),
            fontSize = 18.sp
        )
    }
}