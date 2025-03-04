package com.stats.mania.adapter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.stats.mania.model.Topic
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.stats.mania.R
import com.stats.mania.model.Indicator
import com.stats.mania.model.IndicatorDetail
import com.stats.mania.util.DummyMethods

@Composable
fun IndicatorDetailAdapter(
    indicatorDetail: IndicatorDetail,
    onClick : (IndicatorDetail) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                onClick(indicatorDetail)
            }
            ,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(12.dp)
        ,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = indicatorDetail.country.value,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )
            Spacer(modifier = Modifier.height(2.dp))


            Text(
                text = indicatorDetail.date,
                color = Color.White,

                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = DummyMethods.formatNumber(indicatorDetail.value) ,
                color = Color(0xffffc107),
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )


        }
    }
}
