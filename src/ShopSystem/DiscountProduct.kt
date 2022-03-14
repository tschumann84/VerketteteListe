package ShopSystem

import kotlin.random.Random

class DiscountProduct (productname:String, basePrice:Double, /*chargeOnTop:Double,*/ salesPrice:Double, description:String, val discount:DiscountType): Product(productname, basePrice, salesPrice, description) {
    override val salesPrice = salesPrice * Random.nextDouble(discount.discountFactor)
}
//Auswahlklasse für DiscountProduct
enum class DiscountType(val typeName:String, val discountFactor:Double) {
    S("Sommerschluss mit 20% Rabatt", 0.8),
    K("Kurzes MHD mit 10% Rabatt", 0.9),
    A("Alles muss raus mit 50% Rabatt", 0.5),
    N("Kein Rabatt mit 0% Rabatt", 1.0)
}