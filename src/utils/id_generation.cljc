(ns utils.id-generation)

(def next-id (atom 1))

(defn generate-id
  []
  (let [id @next-id]
    (swap! next-id inc)
    id))
