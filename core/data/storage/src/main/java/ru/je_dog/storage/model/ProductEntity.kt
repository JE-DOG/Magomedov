package ru.je_dog.storage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.je_dog.core.model.ProductDomain
import ru.je_dog.core.model.RatingDomain

@Entity(
    tableName = ProductEntity.TABLE_NAME
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val title: String,
    override val price: Double,
    override val description: String,
    override val category: String,
    override val image: String,
    @Embedded
    override val rating: RatingEntity
): ProductDomain{

    companion object {
        const val TABLE_NAME = "products_table"
    }
}

fun ProductDomain.toEntity() = ProductEntity(
    id,title,price,description,category,image,rating.toEntity()
)

fun RatingDomain.toEntity() = RatingEntity(
    rate, count
)
