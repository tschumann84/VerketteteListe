package ShopSystem

import kotlin.random.Random

class Warehouse() {
    //Liste alle Produkte
    private val products = mutableListOf<Product>()
    //Ausgabe aller Produkte: Name, Menge, Stückpreis, Beschreibung
    val listOfAllProductsInWarehouse: String
        get() {
            var i = 0
            var listall = ""
            for(i in products.indices) {
                listall = listall.plus(products[i].productName).plus("\t\tMenge: ").plus(products[i].availableItems).plus("\t\tStückpreis: ").plus("%.2f".format(products[i].salesPrice)).plus("€\t").plus("\t").plus(products[i].description).plus("\n")
            }
            return listall
        }
    //Rückgabe aller Produkte mit toString
    val productDescriptions: String
        get () {
            val descriptions = products.toString()
            return descriptions
        }
    //        get() {
//            var description = ""
//            for(i in products.indices){
//                description=description.plus("${products[i].description}\n")
//            }
//            return description
//        }
//Ist das Produkt im Warehouse
    fun hasProduct(productName:String):Boolean {
        for(i in products.indices) {
            if (products[i].productName.toUpperCase() == productName.toUpperCase()) return true
        }
        return false
    }
    //Gibt das Produkt anhand des Namens zurück
    fun getProductByName(productName:String): Product? {
        for (i in products.indices) {
            if (products[i].productName.toUpperCase() == productName.toUpperCase()) return products[i]
        }
        return null
    }
    //Füllt das Warehouse
    fun fillWarehouse(productName:String, basePrice:Double, productDescription:String, chargeOnTop:Double = 50.0, initialStockUnits:Int = 3) {
        this.products.add(Product(productName,basePrice,basePrice*chargeOnTop,productDescription))
        val newProduct = this.products.last()
        for (i in 1..initialStockUnits) {
            newProduct.addStock(StockUnit(Random.nextInt(1, 20), Random.nextInt(5,30)))
        }
        newProduct.addReview(PlainReview(Random.nextInt(0,5)))
        newProduct.addReview(LimitedReview(Random.nextDouble(0.0,5.0), "Brauchbar"))
        newProduct.addReview(SmartReview(Random.nextInt(0,5)))
    }
}