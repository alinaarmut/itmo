import re
from rdflib import Graph, Namespace, RDF

g = Graph()
g.parse("/Users/alinaarmut/Desktop/ontology/ontology.rdf", format="xml")
ns = Namespace("http://www.semanticweb.org/alinaarmut/ontologies/2024/8/untitled-ontology-2#")


def get_pokemons_data(pokemon_class):
    pokemons = []
    for individual in g.subjects(RDF.type, ns[pokemon_class]):
        pokemon_data = {
            'Имя': individual.split("#")[-1],
            'Тип': str(g.value(individual, ns['иметь_тип'])).split("#")[-1],
            'Сила': float(g.value(individual, ns['have_power'])) if g.value(individual, ns['have_power']) is not None else None,
            'Легендарность': str(g.value(individual, ns['иметь_rarity'])) if g.value(individual, ns['иметь_rarity']) is not None else None,
            'Эволюция': g.value(individual, ns['иметь_эволюцию']).split("#")[-1] if g.value(individual, ns['иметь_эволюцию']) is not None else None,
            'Друг': g.value(individual, ns['иметь_друга']).split("#")[-1] if g.value(individual, ns[
                'иметь_друга']) is not None else None,

        }

        pokemons.append(pokemon_data)

    return pokemons


def display_pokemons(pokemons):
    print("\nРекомендуемые покемоны:")
    for pokemon in pokemons:
        print(f"Покемоны: {pokemon['Имя']}")


def display_pokemons_all(pokemons):
    print("\nПолная информация:")
    for pokemon in pokemons:
        print(f"Имя: {pokemon['Имя']}")
        print(f"Тип: {pokemon['Тип']}")
        print(f"Сила: {pokemon['Сила']}")
        print(f"Легендарность: {pokemon['Легендарность']}")
        print(f"Эволюция: {pokemon['Эволюция']}")
        print(f"Друг: {pokemon['Друг']}\n")


def base(text):
    pokemon_type_matching = ['Fire', 'Bug', 'Poison', 'Psychic', 'Flying', 'Electric']
    pokemon_type = None
    for type in pokemon_type_matching:
        if type in text:
            pokemon_type = type
            break
    power = None
    rarity = None
    evolve = None
    friend = None

    power_matching = re.search(r'силой\s+больше\s+(\d+)' , text)
    if power_matching:
        power = float(power_matching.group(1))

    rarity_matching = re.search(r'редкостью\s+(обычная|легендарная)', text)
    if rarity_matching:
        rarity = str(rarity_matching.group(1))

    evolve_matching = re.search(r'эволюционируют\s+в\s+(\w+)' , text)
    if evolve_matching:
        evolve = (evolve_matching.group(1))

    friend_matching = re.search(r'друг\s+(\w+)', text)
    if friend_matching:
        friend = (friend_matching.group(1))
    return pokemon_type, power, rarity, evolve, friend

def user_input():
    while True:
        try:
            user_response = input("\nВведите запрос в следующем формате:\n"
                      "Мне нравятся покемоны с типом Fire(обязательное поле), силой больше 5, у которых есть друг Pidgey, "
                      "\nкоторые эволюционируют в Kadabra, и с редкостью легендарная.\n")
            pokemon_type, power, rarity, evolve, friend = base(user_response)
            if pokemon_type:
                recommend = get_pokemons_data('Pokemon')
                filtered_pokemons = []
                if power is not None:
                    for pokemon in recommend:
                        if pokemon['Тип'] == pokemon_type and \
                                pokemon['Сила'] is not None \
                                and pokemon['Сила'] > power and \
                                pokemon['Друг'] is not None and pokemon['Друг'] == friend and \
                                pokemon['Легендарность'] is not None and pokemon['Легендарность'] == rarity and \
                                pokemon['Эволюция'] is not None and pokemon['Эволюция'] == evolve:
                            filtered_pokemons.append(pokemon)
                else:
                    for pokemon in recommend:
                        if pokemon['Тип'] == pokemon_type:
                            filtered_pokemons.append(pokemon)
                if filtered_pokemons:
                    display_pokemons(filtered_pokemons)
                    details = input("Хотите увидеть полную информацию о покемоне? (y - если да): ")
                    if details == 'y':
                        display_pokemons_all(filtered_pokemons)
                else:
                    print("Нет подходящих вариантов по заданным критериям.")
                break
            else:
                print("Некорректный ввод. Убедитесь, что вы указали параметры правильно.")

        except ValueError:
            print("Ошибка ввода. Пожалуйста, введите корректные значения.")


user_input()
