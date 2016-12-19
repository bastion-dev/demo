package rocks.bastion.sushi;

import java.util.*;

import static java.util.Optional.*;

final class SushiRepository {

    private final Map<Long, Sushi> sushiStore;

    SushiRepository() {
        sushiStore = new HashMap<>();
    }

    void create(final Sushi sushi) {
        sushiStore.put(sushi.getId(), sushi);
    }

    List<Sushi> getAll() {
        return new ArrayList<>(sushiStore.values());
    }

    Optional<Sushi> findById(final long id) {
        return ofNullable(sushiStore.get(id));
    }

    Optional<Sushi> findByName(final String name) {
        return sushiStore.values().stream().filter(sushi -> sushi.getName().equals(name)).findFirst();
    }
}
