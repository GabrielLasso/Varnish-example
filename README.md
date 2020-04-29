# Exemplo com o Varnish

## Como rodar esse projeto
É um projeto normal do Ktor, é só rodar pelo IntelliJ. Mais detalhes no [site do Ktor](https://ktor.io/).

## Como rodar o varnish
Primeiro instale o varnish com `sudo pacman -S varnish` (ou usando seu package manager preferido).

Copie o arquivo `default.cvl` para `/etc/varnish/default.cvl` para habilitar o purge.

Em seguida, habilite ele com `systemctl varnish start` (ou o gerenciador de serviços de sua preferência).

O varnish roda por padrão na porta 6081, então rode o projeto no ktor e acesse `localhost:6081/time1`.
O esperado é que toda vez que você acesse essa rota, o tempo mostrado seja o mesmo, enquanto que acessando `localhost:8080/time1` atualize o tempo toda vez que se acesse.

## Como limpar o cache
Para limpar o cache em uma rota, basta fazer um request HTTP com o verbo PURGE para a rota.

Ex:
```
curl -X PURGE 127.0.0.1:6081/time1
```

Note que isso só limpa o cache da rota `/time1`. Se você acessar a rota `/time2`, ela continua cacheada.

## Configurando o cache

O arquivo de configuração é o `default.cvl`.

Nele, a primeira seção diz onde está o servidor que será cacheado:
```
# Default backend definition. Set this to point to your content server.
backend default {
    .host = "127.0.0.1";
    .port = "8080";
}
```
Nesse caso o servidor roda na porta 8080 do `127.0.0.1`

A segunda seção define uma lista de IPs que vão poder apagar o cache:
```
acl purge {
    "localhost";
}
```
Só o localhost que tem essa permissão nesse exemplo.

A próxima seção cria a regra de quando acessar uma rota pelo verbo PURGE a partir de um dos IPs definidos na seção anterior, é para limpar o cache dessa rota:
```
sub vcl_recv {
    # Happens before we check if we have this in cache already.
    #
    # Typically you clean up the request here, removing cookies you don't need,
    # rewriting the request, etc.
    if (req.method == "PURGE") {
        if (!client.ip ~ purge) {
            return(synth(405,"Not allowed."));
        }
        return (purge);
    }
}
```

## Uso no projeto
Para usar o varnish no projeto, a ideia é que toda vez que uma entidade for alterada, o servidor vai executar um request com o verbo PURGE na rota da API que pega essa entidade. Assim a API fica sempre atualizada e é cacheada.