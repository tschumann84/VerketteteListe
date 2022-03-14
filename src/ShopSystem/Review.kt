package ShopSystem

// Interface für Mehrfachvererbung
interface Review{
    fun stars():Int
    fun info():String
}

class PlainReview(val stars:Int):Review {
    override fun stars():Int {
        return stars
    }
    override fun info():String = "Produkt mit ${stars()} Sternen."
}

class LimitedReview(val stars:Double, val comment:String):Review{
    override fun stars():Int {
        return when {
            stars > 5 -> 5
            stars < 0 -> 0
            else -> stars.toInt()
        }
    }
    override fun info():String = comment
}

class SmartReview(val stars:Int):Review{
    override fun stars():Int {
        return stars
    }
    override fun info():String {
        val comment = listOf<String>("Schlechtes Produkt", "Mäßiges Produkt", "Durchschnittliches Produkt", "Brauchbares Produkt", "Gutes Produkt", "Excellentes Produkt")
        if (stars < 0 || stars > 5) return "Nicht sinnvoll bewertet"
        return comment[stars]
    }
}