package com.jeizard.minishop.entities.services

import com.github.javafaker.Faker
import com.jeizard.minishop.activities.ShopActivity
import com.jeizard.minishop.entities.Good
import java.util.Locale

class GoodService {
    var goods = mutableListOf<Good>()

    init {
        val faker: Faker = Faker(Locale("ru"))
        IMAGES.shuffle()
        val dog = faker.dog()
        val commerce = faker.commerce()
        goods = (1..20).map{
            Good(
                id = it.toLong(),
                name = commerce.productName(),
                description = dog.name() + ", " + dog.breed()+ ", " +  dog.gender() + ", " + dog.age() + ", " + dog.size() + ", " + dog.coatLength() + " coat",
                price = commerce.price().toDouble(),
                imageUrl = IMAGES[it % IMAGES.size],
                count = 0
            )
        }.toMutableList()
    }

    companion object{
        private val IMAGES = mutableListOf(
            "https://otkritkis.com/wp-content/uploads/2022/01/6b7884418cfc30cfd18272d71b10e8f1ed50a862751afa8e492daf3cd39732ae.jpg",
            "https://fikiwiki.com/uploads/posts/2022-02/1645008375_28-fikiwiki-com-p-prikolnie-kartinki-s-sobakami-28.jpg",
            "https://i.pinimg.com/originals/f4/33/30/f43330c53671c96d07c1a81cd7442ca0.jpg",
            "https://i.pinimg.com/originals/44/66/7c/44667c37698882eb9d900f840c9a8fae.jpg",
            "https://sun9-17.userapi.com/impg/j3NEJ2rj6QNcFe4hOFgvFJ9aOlaqpmNxZhOx3A/VSsk2qH1dR0.jpg?size=720x960&quality=95&sign=84cee2095f66ceaf6e2830542cd81594&c_uniq_tag=NTxxlisWAXbQAs1f4-i7CkhCEeJDVaZmR59i5WRk694&type=album",
            "https://avatars.mds.yandex.net/i?id=fa583150815ee5317ceb7b9fb3e85fb307a5a7db-10158105-images-thumbs&ref=rim&n=33&w=188&h=250"
        )
    }
}