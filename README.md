# Exemplo com o Varnish

## Como rodar esse projeto
É um projeto normal do Ktor, é só rodar pelo IntelliJ. Mais detalhes no [site do Ktor](https://ktor.io/).

## Como rodar o varnish
Primeiro instale o varnish com `sudo pacman -S varnish` (ou usando seu package manager preferido).

Copie o arquivo `default.cvl` para `/etc/varnish/default.cvl` para habilitar o purge.

Em seguida, habilite ele com `systemctl varnish start` (ou seu gerenciador de serviços de preferência).

O varnish roda por padrão na porta 6081, então rode o projeto no ktor e acesse `localhost:6081/time1`.
O esperado é que toda vez que você acesse essa rota, o tempo mostrado seja o mesmo, enquanto que acessando `localhost:8080/time1` atualize o tempo toda vez que se acesse.

## Como limpar o cache
Para limpar o cache em uma rota, basta fazer um request HTTP com o verbo PURGE para a rota.

Ex:
```
curl -X PURGE 127.0.0.1:6081/time1
```

Note que isso só limpa o cache da rota `/time1`. Se você acessar a rota `/time2`, ela continua cacheada.