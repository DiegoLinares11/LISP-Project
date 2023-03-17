(defun factorial (num)
   (cond
        ((> num 1) (* num (factorial (- num 1))))
        (T 1)
   )
)

(print (factorial 3))
(factorial 3)