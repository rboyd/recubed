(ns recubed)

(def camera (THREE/PerspectiveCamera. 75 (/ window/innerWidth window/innerHeight) 10 10000))
(def scene (THREE/Scene.))
(def projector (THREE/Projector.))
(def renderer (THREE/WebGLRenderer.))
(def cube (THREE/Object3D.))
(def position (js-obj "x" 0 "y" 0))
(def end-pos (js-obj "x" (* 2 Math/PI)
                     "y" (* 2 Math/PI)))
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

(defn cubie
  [[x y z] scale]
  (let [adjusted-scale (* 0.8 scale)
        width    adjusted-scale 
        height   adjusted-scale 
        length   adjusted-scale 
        cube-geo (THREE/CubeGeometry. width height length)
        m-params (js-obj "color" 0x284B7E
                         "wireframe" true
                         "transparent" true)
        material  (THREE/MeshBasicMaterial. m-params)
        cube-mesh (THREE/Mesh. cube-geo material)]
    (aset (.-position cube-mesh) "x" x)
    (aset (.-position cube-mesh) "y" y)
    (aset (.-position cube-mesh) "z" z)
    cube-mesh))
  
(defn add-cube []
  (doseq [x (range 3)
          y (range 3)
          z (range 3)
          :let [cubie-scale 5
                pos (map (partial * cubie-scale) [x y z])]
          :when (not (= x y z 1))]
    (.add cube (cubie pos cubie-scale)))
  (.add scene cube))

(defn ^:export render []
  (init)
  (add-cube)
  (animate))