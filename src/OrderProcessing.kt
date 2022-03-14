import ShopSystem.*


class OrderProcessing {
    /*** Basisstruktur für verkettete Liste ***/
    // Erstes Element der verketten Liste
    var first: OrderNode? = null

    // Ein Knoten der verketteten Liste
    data class OrderNode(val order: Order, var next: OrderNode?)

    /*** Eigenschaften ***/
    // ist die Liste leer?
//    val isEmpty: Boolean = first == null
    val isEmpty: Boolean
        get() {
            return first == null
        }

    // Sind die Items absteigend sortiert?
    fun isSorted(): Boolean{
        var token = first
        while(token != null && token.next != null){
            if(token.order.shoppingCart.totalPrice < token.next!!.order.shoppingCart.totalPrice) return false // !! not null
            token = token.next
        }
        return true
    }

    // Berechnet den Gesamtwert aller Bestellungen
    val totalVolume: Double
        get(){
            var token = first
            var totalVolume = 0.0
            while(token != null){
                totalVolume += token.order.shoppingCart.totalPrice
                token = token.next
            }
            return totalVolume
        }

    // Anzahl der Bestellungen
    val size: Int
        get(){
            var token = first
            var count = 0
            while(token != null){
                count++
                token = token.next
            }
            return count
        }

    // ** Funktionen zum Einfügen **

    fun addFirst(order: Order) {
        first = OrderNode(order, first?.next)
    }
    // Bestellung hinten anhängen
    fun append(order: Order) {
        if (isEmpty){
            addFirst(order)
        } else {
            var token = first
            while(token?.next != null) {
                token = token.next
            }
            token?.next = OrderNode(order, null)
        }
    }

    // Sortiert die Bestellung ein. Details siehe Aufgabentext
    fun insertBeforeSmallerVolumes(order: Order) {
        var token = first
        if(isEmpty || order.shoppingCart.totalPrice > first?.order!!.shoppingCart.totalPrice) {
            addFirst(order)
        } else {
            while(token?.next != null && order.shoppingCart.totalPrice < token.next!!.order.shoppingCart.totalPrice){
                token = token.next
            }
            token?.next = OrderNode(order, token?.next)
        }
    }

    // Sortiert nach Auftragsvolumen
    fun sortyByVolume() {
        var token = first
        first = null
        while(token != null) {
            insertBeforeSmallerVolumes(token.order)
            token = token.next
        }
    }

    // Funktionen zum Verarbeiten der Liste
    fun removeOrder(order: Order){
        var token = first
        if(!isEmpty) {
            if(token?.order == order)  {
                first = first?.next
            } else {
                while (token?.next?.order != order) {
                    token = token?.next
                }
                token?.next = token?.next?.next
            }
        }
    }

    // Verarbeitet die erste Bestellung und entfernt diese aus der Liste
    fun processFirst() {
        if(!isEmpty) {
            first?.order?.shoppingCart!!.buyEverything()
            first = first?.next
        }
    }

    // Vearbeitet die Bestellung mit dem höchsten Auftragsvolumen
    // und entfernt diese aus der Liste
    fun processHighest() {
        var token = first
        var highestOrder = first
        while(token != null) {
            if (highestOrder?.order?.shoppingCart!!.totalPrice < token.order.shoppingCart.totalPrice) {
                highestOrder = token
            }
            token = token.next
        }
        if (first == highestOrder) {
            processFirst()
        } else {
            var token = first
            while (token?.next != highestOrder) {
                highestOrder?.order?.shoppingCart!!.buyEverything()
                token = token?.next
            }
            token?.next = token?.next?.next
        }
    }

    // Verarbeitet alle Aufträge für die Stadt in einem Rutsch
    // und entfernt diese aus der Lite
    fun processAllFor(city: String) {
        var token = first
        while(token != null) {
            if(token.order.address.city == city) {
                removeOrder(token!!.order)
            }
            token = token.next
        }
    }

    // Verarbeite alle Bestellungen. Die Liste ist danach leer.
    fun processAll() {
        var token = first
        while (token != null){
            removeOrder(token!!.order)
            token = token.next
        }
    }

    // ** Funktionen zum Analysieren**

    // Analysiert alle order mit der analyzer Funktion
    fun analyzeAll(analyzer: (Order) -> String): String {
        var string = ""
        var token = first
        while (token != null) {
            string += analyzer(token.order)
            token = token.next
        }
        return string
    }

    // Prüft, ob für ein Produkt einer der Bestellungen
    // die predicate Funktion erfüllt wird
    fun anyProduct(predicate: (Product) -> Boolean): Boolean {
        var token = first
        while(token != null) {
            val copyShoppingCart = token.order.shoppingCart.getShoppingCart()
            for ((product, anzahl) in copyShoppingCart) {
                if (predicate(product)) return true
            }
            token = token.next
        }
        return false
    }

    // Erzeugt ein neues OrderProcessing Objekt, in dem nur noch
    // Order enthalten, für die die predicate Funktion true liefert
    fun filter(predicate: (Order) -> Boolean): OrderProcessing {
        val orderProcessingfilter = OrderProcessing()
        var token = first
        while (token != null) {
            if (predicate(token.order)) {
                orderProcessingfilter.append(token.order)
            }
            token = token.next
        }
        return orderProcessingfilter
    }

}



