package rocks.bastion.sushi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_UP;

class SushiService {

    private final SushiRepository sushiRepository;
    private final AtomicLong atomicLong;

    SushiService() {
        sushiRepository = new SushiRepository();
        atomicLong = new AtomicLong();
    }

    Sushi create(final String name, final double price) {
        if (sushiRepository.findByName(name).isPresent()) {
            throw new SushiException(format("A sushi with the name [%s] already exists.", name));
        }

        if (price <= 0) {
            throw new SushiException(format("[%s] is not a valid price. Prices must be greater than 0.", price));
        }

        final Sushi sushi = new Sushi(atomicLong.incrementAndGet(), name, new BigDecimal(price, new MathContext(2, HALF_UP)));
        sushiRepository.create(sushi);
        return sushi;
    }

    List<Sushi> lookup() {
        return sushiRepository.getAll();
    }

    Optional<Sushi> findById(final int id) {
        return sushiRepository.findById(id);
    }
}
