(ns recubed)

(def camera (THREE/PerspectiveCamera. 75 (/ window/innerWidth window/innerHeight) 10 10000))
(def scene (THREE/Scene.))
(def projector (THREE/Projector.))
(def renderer (THREE/WebGLRenderer.))
(def cube)
(def position (js-obj "x" 0 "y" 0))
(def end-pos (js-obj "x" Math/PI "y" Math/PI))
(def tween (doto (TWEEN/Tween. position)
             (.to end-pos 5000)
             (.easing TWEEN/Easing/Elastic/InOut)
             (.repeat Number.POSITIVE_INFINITY)))

(defn update-rotation []
  (let [pos (.-rotation cube)]
    (set! (.-y pos) (.-y position))
    (set! (.-x pos) (.-x position))))

(defn ^:export init []
  (.add scene camera)
  (.set (.-position camera) 0 0 200)
  (.setSize renderer window/innerWidth window/innerHeight)
  (.appendChild document/body (.-domElement renderer))
  (.onUpdate tween update-rotation)
  (.start tween))

(defn animate []
  (js/requestAnimationFrame animate)
  (TWEEN/update)
  (.render renderer scene camera))

(defn add-cube []
  (let [width    20
        height   20
        length   20
        cube-geo (THREE/CubeGeometry. width height length)
        m-params (js-obj "color" 0x284B7E
                         "wireframe" true
                         "transparent" true)
        material  (THREE/MeshBasicMaterial. m-params)
        cube-mesh (THREE/Mesh. cube-geo material)]
      (def cube cube-mesh)
      (.add scene cube-mesh)))

(defn ^:export render []
  (init)
  (add-cube)
  (animate))