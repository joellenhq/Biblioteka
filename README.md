# Biblioteka

Realizacja prostej aplikacji rozproszonej obsługujacej wypozyczalnie ksiazek z uzyciem technologii Java RMI.

Biblioteka odbiera zadanie odczytu listy ksiazek (wszystkich lub spełniajacych zdefiniowane kryterium) z informacja o ich statusie. Inną zapewnioną funkcjonalnością jest odebranie zadania wypozyczenia okreslonej ksiazki.  Jesli ksiazka jest dostepna odsyła potwierdzenie wypozyczenia.  Jesli ksiazka jest wypozyczona  rejestruje zadanie wypozyczenia w kolejce i odsyła potwierdzenie tego faktu. Jesli ksiazka staje sie dostepna i sa z nia zwiazane zadania wypozyczenia serwer wysyła potwierdzenie wypozyczenia do pierwszego oczekujacego klienta. Możliwe jest odebranie zadania zwrotu ksiazki poprzez zmianę statusu ksiazki.
