package com.stats.mania.adapter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.stats.mania.R
import com.stats.mania.model.Country
import com.stats.mania.model.Topic


@Composable
fun CountryAdapter(
    country: Country,
    onClick : (Country) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                onClick(country)
            }
        ,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
        shape = RoundedCornerShape(12.dp)
        ,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = country.name,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                maxLines = 1
            )
        }
    }
}
