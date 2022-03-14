package ShopSystem

class ShoppingCart {
    fun getShoppingCart(): MutableList<Pair<Product, Int>> {
        return productAndQuantityList
    }
    //Liste mit Produkten und ihrer Anzahl
    private val productAndQuantityList = mutableListOf<Pair<Product, Int>>()
    //Sind alle Produkte in ihrer Anzahl vorhanden?
    val allProductsAvailable: Boolean
        get() {
            var aPA = true
            for ((product, anzahl) in productAndQuantityList) {
                if (anzahl > product.availableItems)
                    aPA = false
            }
            return aPA
        }
    //GesamtPreis aller Produkte im Warenkorb
    val totalPrice:Double
        get() {
            var tP = 0.0
            var i = 0
            for ((product, anzahl) in productAndQuantityList) {
                tP += (productAndQuantityList[i].second * product.salesPrice)
                i++
            }
            return tP
        }
    //Auflistung aller Produkte im Warenkorb[Name, Menge, Stückpreis, Stückpreis*Menge, Gesamteinkaufspreis]
    val listOfAllProducts:String
        get() {
            var listall = ""
            for ((product, anzahl) in productAndQuantityList) {
                listall = listall.plus(product.productName).plus("\t\tMenge: ").plus(anzahl).plus("\t\tStückpreis: ").plus(product.salesPrice).plus(" €").plus("\t\tGesamt: ").plus("%.2f".format(product.salesPrice * anzahl)).plus(" €\n\n").plus("Gesamter Warenkorb: ").plus("%.2f".format(totalPrice)).plus(" €\n\n")
            }
            return listall
        }
    //Entfernt alle Einträge die es selbst besitzt
    fun clear() {
        productAndQuantityList.removeAll(this.productAndQuantityList)
    }
    //Kauft alles aus dem Warenkorb, alle Produkte in ihrer Anzahl werden sortiert nach ablaufdatum aus StockUnits entfernt, solange es genug davon gibt. Gesamteinkaufssumme wird zurückgegeben
    fun buyEverything():Double {
        var summe:Double = 0.0
        for((product, quantity) in productAndQuantityList) {
            summe += (product.salesPrice * product.takeItems(quantity))
        }
        productAndQuantityList.clear()
        return summe
    }
    //Produkt mit Anzahl zum Warenkorb hinzufügen
    fun addProduct(product: Product?, quantity: Int) {
        if (product != null) {
            productAndQuantityList.add(Pair(product, quantity))
        }
    }
    fun add(product: Product?, quantity: Int) {
        if (product != null) {
            productAndQuantityList.add(Pair(product, quantity))
        }
    }
    //ISt dieses Produkt schon im Warenkorb
    fun isProductExist(productName:String):Boolean {
        for ((product) in productAndQuantityList) {
            if (product.productName.toUpperCase() == productName.toUpperCase())
                return true
        }
        return false
    }
}