package main

import (
	"fmt"
	"net/http"
	"time"
)

func main() {
	http.ListenAndServe(":8080", http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if r.URL.Path == "/" {
			w.Write([]byte(`<!doctype html>
<div id=app></div>
<script>
const $app = document.getElementById('app')
const source = new EventSource('/data')
source.addEventListener('message', (ev) => {
	console.log(ev)
	//app.innerHTML += ev.data + "<br>"
	app.innerHTML = ev.data
})
</script>`))
		}

		if r.URL.Path == "/data" {
			w.Header().Set("Content-Type", "text/event-stream")
			w.Header().Set("Cache-Control", "no-cache")
			w.WriteHeader(http.StatusOK)

			for {
				data := time.Now().Format(time.RFC3339)
				fmt.Fprintf(w, "data: %s\n\n", data)
				w.(http.Flusher).Flush()

				select {
				case <-time.After(time.Second):
				case <-r.Context().Done():
					return
				}
			}
		}
	}))
}
