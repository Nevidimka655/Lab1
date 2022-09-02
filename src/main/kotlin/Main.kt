enum class EditOptions {
    NAME, MANUFACTURER, QUANTITY, PRICE;
    companion object {
        fun fromValue(value: Int) = values().find { it.ordinal == value }
    }
}

class Product {
    var name = ""
    var manufacturer = ""
    var quantity = 0
    var price = 0.0
}

fun main(args: Array<String>) {
    println("Введіть кількість продуктів:")
    val productCount = readln().toInt()
    val products = arrayListOf<Product>()
    for (i in 1 until productCount + 1) {
        if (i > 1) println("\n===========================\n")
        val newProduct = Product().apply {
            println("Введіть найменування товару #$i:")
            name = readln().trim()
            println("Введіть виробника товару #$i:")
            manufacturer = readln().trim()
            println("Введіть кількість товару #$i:")
            quantity = readln().trim().toInt()
            println("Введіть вартість товару #$i:")
            price = readln().trim().toDouble()
        }
        products.add(newProduct)
    }
    println("\n===========================\n")
    products.forEachIndexed { i, product ->
        println("${i + 1})\t${product.name}\t${product.manufacturer}\t{${product.quantity}}\t${product.price}$")
    }
    println("\n===========================\n")
    println("Загальна кількість товарів: ${products.size}")
    val maxQuantityProductsIds = arrayListOf<Product>()
    var maxQuantityValue = 0
    products.forEach {
        when {
            it.quantity > maxQuantityValue -> {
                maxQuantityValue = it.quantity
                with(maxQuantityProductsIds) {
                    clear()
                    add(it)
                }
            }
            it.quantity == maxQuantityValue -> maxQuantityProductsIds.add(it)
        }
    }
    println("Найбільші товари за кількістю:")
    maxQuantityProductsIds.forEachIndexed { i, it ->
        println("${i + 1})\t${it.name}\t${it.manufacturer}\t{${it.quantity}}\t${it.price}$")
    }
    var priceAverage = 0.0
    products.forEach { priceAverage += it.price }
    priceAverage /= products.size
    println("Середня ціна: $priceAverage")
    val lowerAveragePriceCount = products.filter { it.price < priceAverage }.size
    println("Кількість товарів, у яких ціна нижча за середню: $lowerAveragePriceCount")
    println("Впорядкований список товарів за спаданням ціни:")
    products.sortedByDescending { it.price }.forEachIndexed { i, product ->
        println("${i + 1})\t${product.name}\t${product.manufacturer}\t{${product.quantity}}\t${product.price}$")
    }
    println("\n===========================\n")
    println("Введіть назву/частину назви товару для пошуку:")
    val searchQuery = readln().trim()
    println("Результати пошуку:")
    val searchResultArray = products.filter {
        it.name.contains(other = searchQuery, ignoreCase = true)
    }
    searchResultArray.forEachIndexed { i, product ->
        println("${i + 1})\t${product.name}\t${product.manufacturer}\t{${product.quantity}}\t${product.price}$")
    }
    println("\n===========================\n")
    if (searchResultArray.isNotEmpty()) {
        println("Введіть номер товару для редагування:")
        val productIdFromSearchToEdit = readln().toInt() - 1
        if (searchResultArray.size - productIdFromSearchToEdit > 0) {
            val productToEdit = searchResultArray[productIdFromSearchToEdit]
            with(productToEdit) { println("$name\t$manufacturer\t{$quantity}\t$price$") }
            println("\n${EditOptions.NAME.ordinal}) Назва")
            println("${EditOptions.MANUFACTURER.ordinal}) Виробник")
            println("${EditOptions.QUANTITY.ordinal}) Кількість")
            println("${EditOptions.PRICE.ordinal}) Ціна\n")
            println("Введіть номер поля для редагування:")
            EditOptions.fromValue(readln().toInt())?.let {
                val indexInOriginalArray = products.indexOf(productToEdit)
                when(it) {
                    EditOptions.NAME -> {
                        println("Введіть найменування товару:")
                        productToEdit.name = readln().trim()
                    }
                    EditOptions.MANUFACTURER -> {
                        println("Введіть виробника товару:")
                        productToEdit.manufacturer = readln().trim()
                    }
                    EditOptions.QUANTITY -> {
                        println("Введіть кількість товару:")
                        productToEdit.quantity = readln().trim().toInt()
                    }
                    EditOptions.PRICE -> {
                        println("Введіть вартість товару:")
                        productToEdit.price = readln().trim().toDouble()
                    }
                }
                products[indexInOriginalArray] = productToEdit
                with(productToEdit) { println("$name\t$manufacturer\t{$quantity}\t$price$") }
            } ?: println("Некорректний номер поля!")
        } else println("Некорректний номер товару!")
    } else println("Немає результатів")
}