(ns recubed)

(def camera (THREE/PerspectiveCamera. 75 (/ window/innerWidth window/innerHeight) 10 10000))
(def scene (THREE/Scene.))
(def projector (THREE/Projector.))
(def renderer (THREE/WebGLRenderer.))

(defn ^:export init []
  (.add scene camera)
  (.set (.-position camera) 0 0 200)
  (.setSize renderer window/innerWidth window/innerHeight)
  (.appendChild document/body (.-domElement renderer)))

(defn animate []
  (js/requestAnimationFrame animate)
  (.render renderer scene camera))

(defn add-cube []
  (let [width    20
        height   20
        length   20
        cube-geo (THREE/CubeGeometry. width height length)
        m-params (js-obj "color" 0x284B7E)
        material  (THREE/MeshBasicMaterial. m-params)
        cube-mesh (THREE/Mesh. cube-geo material)]
      (.add scene cube-mesh)))

(defn ^:export render []
  (init)
  (add-cube)
  (animate))