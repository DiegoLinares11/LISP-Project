; Fibonacci function
(defun fibonacci (n)
    (cond
        ((< n 2) n) ; else try
        (T ( + (fibonacci (- n 1)) (fibonacci (- n 2))))
    )
)

; Calling function
(print (fibonacci 10))
(fibonacci 5)
(fibonacci 5)
(fibonacci 5)
