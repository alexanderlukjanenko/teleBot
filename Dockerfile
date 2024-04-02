FROM golang:1.22.1 as builder 

WORKDIR /go/src/app
COPY . . 
RUN make build 



FROM scratch 
WORKDIR /
COPY --from=builder /go/src/app/build/kbot .
COPY --from=alpine:latest /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/
ENTRYPOINT ["./kbot"]