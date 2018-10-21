#lang racket
;; number number -> number

(define (power-head a b)

  (cond

    [(= b 0) 1]

    [else (* a (power-head a (- b 1)))]))

 

;;number number number -> number

(define (power a b n)

  (cond

    [(= b 0) n]

    [else (let ((m (- b 1)))

           (power a m (* n a)))]))

 

;;definir power-tail number number -> number

(define (power-tail a b)

  (power a b 1))

 

;;pruebas de Power

(power-head 4 3)

(power-tail 4 3)

 

;;list number -> number

;; si lista es menor a 3 devuelve '()

(define (thirdi list a)

  (cond

    [(= a 1) (car list)]

    [else (let ((x (- a 1)))

            (cond

              [(empty? (cdr list)) '()]

              [else (thirdi (cdr list) x)]))]))

 

;; cambio de nombre por <definition> de third

;; list -> number

(define (thirda list)

  (thirdi list 3))

 

;;prueba third

(thirda (cons 1(cons 2 (cons 3 (cons 4 (cons 5 empty))))))

(thirda (cons 4 (cons 5 empty)))

 

;;list number -> bool

;;practicar con and

(define (just-two list x)

  (if (and (= x 2)(empty? (cdr list)))

          (empty? (cdr list))

          (just-two (cdr list) (+ x 1))))

 

;; list -> bool

(define (just-two? list)

  (just-two list 0))

 

(just-two? (cons 1 empty))

