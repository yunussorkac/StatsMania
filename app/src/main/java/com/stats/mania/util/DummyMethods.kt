package com.stats.mania.util

class DummyMethods {

    companion object {

        fun formatNumber(value: Double): String {
            return when {
                value >= 1_000_000_000_000 -> String.format("%.1fT", value / 1_000_000_000_000) // Trilyon (T)
                value >= 1_000_000_000 -> String.format("%.1fB", value / 1_000_000_000) // Milyar (B)
                value >= 1_000_000 -> String.format("%.1fM", value / 1_000_000) // Milyon (M)
                value >= 1_000 -> String.format("%.1fK", value / 1_000) // Bin (K)
                else -> String.format("%.1f", value) // Küçük sayılar için normal gösterim
            }
        }



    }


}