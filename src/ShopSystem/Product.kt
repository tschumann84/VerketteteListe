package ShopSystem

open class Product(val productName:String, val basePrice:Double, open val salesPrice:Double, val description:String) {

    //Liste aller Bewertungen des Produkts
    val reviews = mutableListOf<Review>()
    //Liste aller StockUnit´s des Produkts
    val stockUnit = mutableListOf<StockUnit>()
    //Profit aus dem Verkauf
    val profitPerItem:Double = salesPrice - basePrice
    //Summe aller StockUnit´s vom selben Product
    val availableItems:Int
        get() {
            var result = 0
            for (i in this.stockUnit) {
                result += i.quantity
            }
            return result
        }
    //Gesamt Einkaufspreis
    val valueOfAllItems:Double = availableItems * basePrice
    //Gesamt Verkaufspreis
    val salesValueOfAllItems:Double = availableItems * salesPrice
    //Veränderte Methode .toString
    override fun toString():String {
        return "$productName $salesPrice Euro $description.\n"
    }
    //Produkt hinzufügen als StockUnit
    fun addStock(items:StockUnit) {
        stockUnit.add(items)
    }
    //Bewertung hinzufügen
    fun addReview(review:Review):String{
        return ("$productName wurde mit ${review.stars()} Sternen bewertet. Kommentar: ${review.info()}")
    }
    //Entfernt alle leeren Produkte oder abgelaufene
    fun cleanStock() {
        for (i in stockUnit.indices) {
            if (stockUnit[i].isExpired) stockUnit.removeAt(i)
            if (stockUnit[i].quantity == 0) stockUnit.removeAt(i)
        }
    }
    //Gewünschte Anzahl des Produkts verfügbar
    fun isPreferredQuantityAvailable(preferredQuantity:Int):Boolean {
        return preferredQuantity >= availableItems
    }
    //Produkt entnahme aus Lagerbeständen, vorbereitung und rückgabewert
    fun takeItems(preferredQuantity:Int):Int {
        var result:Int
        if(preferredQuantity <= availableItems) {
            result = preferredQuantity
            realTakeItems(preferredQuantity)
//        return preferredQuantity
        } else {
            result = availableItems
            realTakeItems(availableItems)
//        return availableItems
        }
        return result
    }
    //stockUnits nach ablaufdatum sortiert
    private fun sortStockUnit() = stockUnit.sortBy{it.daysBeforeExpiration}
    //Produkt entnahme aus sortierten stockUnits mit counter
    private fun realTakeItems(zahl:Int) {
        sortStockUnit()
        var anzahl = zahl
        while(anzahl != 0) {
            if (anzahl >= stockUnit[0].quantity) {
                anzahl =- stockUnit[0].quantity
                stockUnit.removeAt(0)
            } else {
                stockUnit[0].quantity -= anzahl
                anzahl = 0
            }
        }

    }
}