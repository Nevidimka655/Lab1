// enum клас констант для відображення значень параметрів сутності "Product"
enum class ProductParams {
    NAME, MANUFACTURER, QUANTITY, PRICE;

    companion object {
        // метод повертає значення $ProductParams з value
        fun fromValue(value: Int) = values().find { it.ordinal == value }
    }
}

// Сутність "Товар"
data class Product(
    val name: String = "",
    val manufacturer: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0
)

private val products by lazy { arrayListOf<Product>() }
private var searchResults = arrayListOf<Product>()

// Точка входу програми
fun main(args: Array<String>) {
    queryProducts() // робимо запит продуктів
    printDivider() // виводимо роздільник
    printProducts(products = products) // виводимо список products
    printDivider() // виводимо роздільник
    printProductsCount() // виводимо кількість продуктів
    printMaxQuantityProducts() // виводимо товари з найбільшою кількістю
    printAveragePrice() // виводимо середню ціну
    printLowerAveragePriceCount() // виводимо товари, нижче за середню ціну
    printDescByPrice() // виводимо
    printDivider() // виводимо роздільник
    submitSearchQuery(searchQuery = querySearchText())
    printSearchResults()
    if (searchResults.isNotEmpty()) {
        printDivider() // виводимо роздільник
        performEditFromSearch()
    }
}

fun printDivider() = println("\n===========================\n")

fun printProductsCount() = println("Загальна кількість товарів: ${products.size}")

fun printProduct(product: Product, prefix: String = "") =
    println("$prefix${product.name}\t${product.manufacturer}\t{${product.quantity}}\t${product.price}$")

fun printProducts(products: ArrayList<Product>) = products.forEachIndexed { i, product ->
    printProduct(product, "${i + 1})\t")
}

fun printMaxQuantityProducts() {
    println("Найбільші товари за кількістю:")
    printProducts(products = fetchMaxQuantityArray())
}

fun printAveragePrice() = println("Середня ціна: ${fetchAveragePrice()}")

fun printLowerAveragePriceCount() =
    println("Кількість товарів, у яких ціна нижча за середню: ${fetchLowerAveragePriceCount()}")

fun printDescByPrice() {
    println("Впорядкований список товарів за спаданням ціни:")
    printProducts(products = fetchDescSortedByPrice())
}

fun printSearchResults() {
    println("Результати пошуку:")
    if (searchResults.isNotEmpty()) printProducts(products = searchResults)
    else printNoResults()
}

fun printProductParams() {
    println("\n${ProductParams.NAME.ordinal}) Назва")
    println("${ProductParams.MANUFACTURER.ordinal}) Виробник")
    println("${ProductParams.QUANTITY.ordinal}) Кількість")
    println("${ProductParams.PRICE.ordinal}) Ціна\n")
}

fun printError() = println("Помилка!")
fun printNoResults() = println("Немає результатів")

// універсальний метод для запиту та введення
fun queryInput(reason: String) = run { // повертає останню лінію блоку
    println("$reason:")
    readln().trim()
}

fun queryName(suffix: String = "") = queryInput("Введіть найменування товару$suffix")
fun queryManufacturer(suffix: String = "") = queryInput("Введіть виробника товару$suffix")
fun queryQuantity(suffix: String = "") = queryInput("Введіть кількість товару$suffix").toInt()
fun queryPrice(suffix: String = "") = queryInput("Введіть вартість товару$suffix").toDouble()
fun queryProductParam() = queryInput("Введіть номер поля для редагування").toInt()
fun querySearchText() = queryInput("Введіть назву/частину назви товару для пошуку")
fun querySearchProductIdToEdit(): Int {
    val id = queryInput("Введіть номер товару для редагування").toInt() - 1
    return if (searchResults.size - id > 0) id else {
        printError()
        querySearchProductIdToEdit()
    }
}

fun queryProducts() {
    val productCount = queryInput("Введіть кількість продуктів").toInt()
    if (productCount < 1) queryProducts()
    for (i in 1 until productCount + 1) {
        if (i > 1) printDivider() // виводимо роздільник
        val suffix = " #$i"
        val newProduct = Product(
            name = queryName(suffix),
            manufacturer = queryManufacturer(suffix),
            quantity = queryQuantity(suffix),
            price = queryPrice(suffix)
        )
        products.add(newProduct) // додаємо до товарів
    }
}

fun fetchMaxQuantityArray() = arrayListOf<Product>().apply {
    var maxQuantityValue = 0
    products.forEach {
        when {
            // якщо теперішнє значення більше збереженого, то виконуємо очищення списку з заміною
            it.quantity > maxQuantityValue -> {
                maxQuantityValue = it.quantity
                clear()
                add(it)
            }
            // якщо значення єквівалетно збереженому
            it.quantity == maxQuantityValue -> add(it)
        }
    }
}

fun fetchLowerAveragePriceCount() = run {
    val priceAverage = fetchAveragePrice()
    products.filter { it.price < priceAverage }.size
}

fun fetchAveragePrice() = run {
    var priceAverage = 0.0
    products.forEach { priceAverage += it.price }
    priceAverage /= products.size
    priceAverage
}

fun fetchDescSortedByPrice() = products.sortedByDescending { it.price }.toMutableList() as ArrayList

fun submitSearchQuery(searchQuery: String) {
    searchResults = products.filter {
        it.name.contains(other = searchQuery, ignoreCase = true)
    }.toMutableList() as ArrayList
}

fun performEditFromSearch() {
    println("Редагування")
    println()
    val selectedProductToEdit = searchResults[querySearchProductIdToEdit()]
    printProduct(selectedProductToEdit)
    printProductParams()
    ProductParams.fromValue(queryProductParam())?.let { // якщо значення ProductParams != null
        val indexInOriginalArray = products.indexOf(selectedProductToEdit)
        products[indexInOriginalArray] = when (it) {
            ProductParams.NAME -> selectedProductToEdit.copy(name = queryName())
            ProductParams.MANUFACTURER -> selectedProductToEdit.copy(manufacturer = queryManufacturer())
            ProductParams.QUANTITY -> selectedProductToEdit.copy(quantity = queryQuantity())
            ProductParams.PRICE -> selectedProductToEdit.copy(price = queryPrice())
        }
        printProduct(products[indexInOriginalArray]) // заміна єлементу
    } ?: run { // елвіс оператор, якщо значення ProductParams == null
        printError()
        performEditFromSearch()
    }
}