package ShopSystem

class StockUnit(var quantity:Int, var daysBeforeExpiration:Int) {
    var isExpired: Boolean = daysBeforeExpiration <= 0
    var isExpiringSoon: Boolean = daysBeforeExpiration <= 5
}
