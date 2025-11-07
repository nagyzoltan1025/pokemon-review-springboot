package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PokemonRepositoryTests {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    void PokemonRepository_SaveAll_ReturnsSavedPokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    void PokemonRepository_GetAll_ReturnMoreThenOnePokemon() {
        Pokemon pokemon1 = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        Pokemon pokemon2 = Pokemon.builder()
                .name("pickachu")
                .build();

        pokemonRepository.save(pokemon1);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    void PokemonRepository_FindById_ReturnPokemon() {
        Pokemon pokemon1 = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemonList = pokemonRepository.findById(pokemon1.getId()).get();

        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        Pokemon pokemon1 = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemonList = pokemonRepository.findByType(pokemon1.getType()).get();

        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
        Pokemon pokemon1 = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemonSave = pokemonRepository.findById(pokemon1.getId()).get();
        pokemonSave.setType("Electric");
        pokemonSave.setName("Raichu");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonSave);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());

        Assertions.assertThat(pokemonReturn).isEmpty();
    }
}
