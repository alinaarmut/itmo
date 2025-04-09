package org.example.service;

import jakarta.transaction.Transactional;
import org.example.essence.Ad;
import org.example.essence.repository.AdRepository;
import org.springframework.stereotype.Service;

@Service
public class AdService {

    private final AdRepository advertisementRepository;

    public AdService(AdRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }
    @Transactional
    public void createAdvertisements() {
        if (advertisementRepository.count() == 0) {
            advertisementRepository.save(new Ad(1L, "Уютная квартира в центре города", "Просторная двухкомнатная квартира с видом на парк, рядом с основными достопримечательностями города. Идеально подходит для деловых поездок и отдыха.", 200.0));
            advertisementRepository.save(new Ad(2L, "Современный дом с бассейном", "Роскошный дом с собственным бассейном, большим садом и парковкой. Отличное место для семейного отдыха или уединённого проживания на природе.", 350.0));
            advertisementRepository.save(new Ad(3L, "Студия у моря", "Уютная студия с балконом и видом на море. Современный интерьер и удобное местоположение для пляжного отдыха.", 180.0));
            advertisementRepository.save(new Ad(4L, "Коттедж в горном курорте", "Уединённый коттедж в горах с камином и сауней. Идеально подходит для зимнего отдыха, катания на лыжах и прогулок по горным тропам.", 250.0));
            advertisementRepository.save(new Ad(5L, "Апартаменты в историческом центре", "Элегантные апартаменты в старинном здании с высокими потолками и атмосферой прошлого века. Удобно для длительного проживания.", 220.0));
            advertisementRepository.save(new Ad(6L, "Шикарный пентхаус с террасой", "Пентхаус с панорамным видом на город, огромной террасой и стильным современным интерьером. Для тех, кто ценит роскошь и комфорт.", 500.0));
            advertisementRepository.save(new Ad(7L, "Лофт с высокими потолками", "Модный лофт с индустриальным стилем и дизайнерской мебелью. Подходит для творческих людей и тех, кто ищет нестандартные решения в интерьере.", 160.0));
            advertisementRepository.save(new Ad(8L, "Бунгало на берегу озера", "Уютное бунгало на берегу озера, окружённое природой. Место для спокойного отдыха, рыбалки и прогулок на свежем воздухе.", 150.0));
        }
    }
}
