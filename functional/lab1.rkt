#lang racket

(define (areaTriangulo b h)
  (/ (* b h) 2))

(define (a n)
  (+ 10 (expt n 2)))

(define (b n)
  (+ 20 (* (expt n 2) (/ 1 2))))

(define (c n)
  (- 2 (/ 1 n)))

(define (solutions a b c)
  (cond
    [(> (bsq b) (multi a c)) 2]
    [(= (bsq b) (multi a c)) 1]
    [(< (bsq b) (multi a c)) 0]))

(define (bsq x)
  (expt x 2))

(define (multi a c)
  (* 4 (* a c)))

(solutions 1 0 -1)
(solutions 2 4 2)
