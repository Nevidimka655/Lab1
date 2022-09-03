// головний клас

// enum клас констант для відображення значень параметрів сутності "Product"
enum class ProductParams {
    NAME, MANUFACTURER, QUANTITY, PRICE;
    companion object {
        // метод повертає значення $ProductParams з value
        fun fromValue(value: Int) = values().find { it.ordinal == value }
    }
}

// Сутність "Товар"
class Product {
    var name = ""
    var manufacturer = ""
    var quantity = 0
    var price = 0.0
}

// Точка входу програми
fun main(args: Array<String>) {
    // робимо запит продуктів
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
    println("\n===========================\n") // виводимо роздільник
    // виводимо список products
    products.forEachIndexed { i, product ->
        println("${i + 1})\t${product.name}\t${product.manufacturer}\t{${product.quantity}}\t${product.price}$")
    }
    println("\n===========================\n") // виводимо роздільник
    // виводимо кількість продуктів
    println("Загальна кількість товарів: ${products.size}")
    // виводимо товари з найбільшою кількістю
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
    // виводимо середню ціну
    var priceAverage = 0.0
    products.forEach { priceAverage += it.price }
    priceAverage /= products.size
    println("Середня ціна: $priceAverage")
    // виводимо товари, нижче за середню ціну
    val lowerAveragePriceCount = products.filter { it.price < priceAverage }.size
    println("Кількість товарів, у яких ціна нижча за середню: $lowerAveragePriceCount")
    // виводимо товари за спаданням ціни
    println("Впорядкований список товарів за спаданням ціни:")
    products.sortedByDescending { it.price }.forEachIndexed { i, product ->
        println("${i + 1})\t${product.name}\t${product.manufacturer}\t{${product.quantity}}\t${product.price}$")
    }
    println("\n===========================\n") // виводимо роздільник
    println("Введіть назву/частину назви товару для пошуку:")
    // викликаємо функцію пошуку
    val searchQuery = readln().trim()
    // виводимо результати пошуку
    println("Результати пошуку:")
    val searchResultArray = products.filter {
        it.name.contains(other = searchQuery, ignoreCase = true)
    }
    if (searchResultArray.isNotEmpty()) {
        searchResultArray.forEachIndexed { i, product ->
            println("${i + 1})\t${product.name}\t${product.manufacturer}\t{${product.quantity}}\t${product.price}$")
        }
    } else println("Немає результатів")
    println("\n===========================\n") // виводимо роздільник
    if (searchResultArray.isNotEmpty()) {
        // викликаємо меню редагування знайдених товарів
        println("Введіть номер товару для редагування:")
        val productIdFromSearchToEdit = readln().toInt() - 1
        if (searchResultArray.size - productIdFromSearchToEdit > 0) {
            val productToEdit = searchResultArray[productIdFromSearchToEdit]
            with(productToEdit) { println("$name\t$manufacturer\t{$quantity}\t$price$") }
            println("\n${ProductParams.NAME.ordinal}) Назва")
            println("${ProductParams.MANUFACTURER.ordinal}) Виробник")
            println("${ProductParams.QUANTITY.ordinal}) Кількість")
            println("${ProductParams.PRICE.ordinal}) Ціна\n")
            println("Введіть номер поля для редагування:")
            ProductParams.fromValue(readln().toInt())?.let {
                val indexInOriginalArray = products.indexOf(productToEdit)
                when(it) {
                    ProductParams.NAME -> {
                        println("Введіть найменування товару:")
                        productToEdit.name = readln().trim()
                    }
                    ProductParams.MANUFACTURER -> {
                        println("Введіть виробника товару:")
                        productToEdit.manufacturer = readln().trim()
                    }
                    ProductParams.QUANTITY -> {
                        println("Введіть кількість товару:")
                        productToEdit.quantity = readln().trim().toInt()
                    }
                    ProductParams.PRICE -> {
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