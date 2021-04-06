--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.3

-- Started on 2021-04-06 09:31:21

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 208 (class 1255 OID 16604)
-- Name: stat(date, date); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.stat(start_d date, end_d date) RETURNS TABLE(name text, surname text, prod text, price numeric)
    LANGUAGE sql
    AS $$
select customer.surname, customer.name, goods.name, sum(goods.price) from customer join purchases on customer.id = purchases.id_c
join goods on goods.id = purchases.id_g where p_date > start_d and p_date < end_d group by customer.surname, customer.name, goods.name order by customer.surname, sum desc;
$$;


ALTER FUNCTION public.stat(start_d date, end_d date) OWNER TO admin;

--
-- TOC entry 209 (class 1255 OID 16606)
-- Name: test_select(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.test_select() RETURNS TABLE(id integer, name text)
    LANGUAGE sql
    AS $$
select id, name from customer;
$$;


ALTER FUNCTION public.test_select() OWNER TO admin;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 16566)
-- Name: customer; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    name character varying(20),
    surname character varying(40)
);


ALTER TABLE public.customer OWNER TO admin;

--
-- TOC entry 202 (class 1259 OID 16564)
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_id_seq OWNER TO admin;

--
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 202
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;


--
-- TOC entry 205 (class 1259 OID 16574)
-- Name: goods; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.goods (
    id integer NOT NULL,
    name character varying(40),
    price numeric
);


ALTER TABLE public.goods OWNER TO admin;

--
-- TOC entry 204 (class 1259 OID 16572)
-- Name: goods_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.goods_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.goods_id_seq OWNER TO admin;

--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 204
-- Name: goods_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.goods_id_seq OWNED BY public.goods.id;


--
-- TOC entry 207 (class 1259 OID 16585)
-- Name: purchases; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.purchases (
    id integer NOT NULL,
    id_c integer,
    id_g integer,
    p_date date
);


ALTER TABLE public.purchases OWNER TO admin;

--
-- TOC entry 206 (class 1259 OID 16583)
-- Name: purchases_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.purchases_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchases_id_seq OWNER TO admin;

--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 206
-- Name: purchases_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;


--
-- TOC entry 2750 (class 2604 OID 16569)
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- TOC entry 2751 (class 2604 OID 16577)
-- Name: goods id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.goods ALTER COLUMN id SET DEFAULT nextval('public.goods_id_seq'::regclass);


--
-- TOC entry 2752 (class 2604 OID 16588)
-- Name: purchases id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);


--
-- TOC entry 2888 (class 0 OID 16566)
-- Dependencies: 203
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.customer VALUES (1, 'Ivan', 'Petrov');
INSERT INTO public.customer VALUES (2, 'Oleg', 'Ivanov');
INSERT INTO public.customer VALUES (3, 'Viktoria', 'Semenova');
INSERT INTO public.customer VALUES (4, 'Anna', 'Anderson');
INSERT INTO public.customer VALUES (5, 'Petr', 'Petrov');
INSERT INTO public.customer VALUES (6, 'Anna', 'Semenova');
INSERT INTO public.customer VALUES (7, 'Marina', 'Ivanova');
INSERT INTO public.customer VALUES (8, 'Maria', 'Romanova');
INSERT INTO public.customer VALUES (9, 'Roman', 'Bobrov');
INSERT INTO public.customer VALUES (10, 'Max', 'Bobrov');
INSERT INTO public.customer VALUES (11, 'Viktor', 'Imamov');


--
-- TOC entry 2890 (class 0 OID 16574)
-- Dependencies: 205
-- Data for Name: goods; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.goods VALUES (1, 'milk', 60);
INSERT INTO public.goods VALUES (2, 'bread', 30);
INSERT INTO public.goods VALUES (3, 'eggs', 85);
INSERT INTO public.goods VALUES (4, 'cheese', 250);
INSERT INTO public.goods VALUES (5, 'butter', 120);
INSERT INTO public.goods VALUES (6, 'condensed milk', 94);
INSERT INTO public.goods VALUES (7, 'sausage', 145);
INSERT INTO public.goods VALUES (8, 'chicken', 415);
INSERT INTO public.goods VALUES (9, 'pancakes', 85);
INSERT INTO public.goods VALUES (10, 'flour', 128);


--
-- TOC entry 2892 (class 0 OID 16585)
-- Dependencies: 207
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.purchases VALUES (1, 1, 2, '2021-01-10');
INSERT INTO public.purchases VALUES (2, 2, 3, '2021-01-20');
INSERT INTO public.purchases VALUES (3, 3, 4, '2021-01-22');
INSERT INTO public.purchases VALUES (4, 4, 5, '2021-01-30');
INSERT INTO public.purchases VALUES (5, 1, 1, '2021-01-10');
INSERT INTO public.purchases VALUES (6, 1, 5, '2021-01-10');
INSERT INTO public.purchases VALUES (8, 1, 4, '2021-01-10');
INSERT INTO public.purchases VALUES (9, 2, 4, '2021-01-21');
INSERT INTO public.purchases VALUES (10, 2, 1, '2021-01-21');
INSERT INTO public.purchases VALUES (11, 3, 5, '2021-01-22');
INSERT INTO public.purchases VALUES (12, 1, 2, '2021-01-11');
INSERT INTO public.purchases VALUES (13, 1, 2, '2021-01-12');
INSERT INTO public.purchases VALUES (14, 1, 2, '2021-01-12');
INSERT INTO public.purchases VALUES (15, 2, 3, '2021-01-20');
INSERT INTO public.purchases VALUES (16, 2, 3, '2021-01-22');
INSERT INTO public.purchases VALUES (17, 3, 5, '2021-01-25');
INSERT INTO public.purchases VALUES (18, 3, 5, '2021-01-27');
INSERT INTO public.purchases VALUES (19, 4, 4, '2021-01-10');
INSERT INTO public.purchases VALUES (20, 4, 4, '2021-01-10');
INSERT INTO public.purchases VALUES (21, 4, 4, '2021-01-10');
INSERT INTO public.purchases VALUES (22, 4, 3, '2021-01-15');
INSERT INTO public.purchases VALUES (23, 3, 2, '2021-01-25');
INSERT INTO public.purchases VALUES (24, 3, 2, '2021-01-26');
INSERT INTO public.purchases VALUES (25, 4, 2, '2021-01-27');
INSERT INTO public.purchases VALUES (26, 5, 9, '2021-02-01');
INSERT INTO public.purchases VALUES (27, 5, 9, '2021-02-02');
INSERT INTO public.purchases VALUES (28, 5, 9, '2021-02-03');
INSERT INTO public.purchases VALUES (29, 5, 9, '2021-02-04');
INSERT INTO public.purchases VALUES (30, 6, 7, '2021-01-30');
INSERT INTO public.purchases VALUES (31, 6, 1, '2021-01-30');
INSERT INTO public.purchases VALUES (32, 6, 6, '2021-01-30');
INSERT INTO public.purchases VALUES (33, 6, 8, '2021-01-30');
INSERT INTO public.purchases VALUES (34, 7, 10, '2021-02-05');
INSERT INTO public.purchases VALUES (35, 7, 5, '2021-02-05');
INSERT INTO public.purchases VALUES (36, 7, 3, '2021-02-05');
INSERT INTO public.purchases VALUES (37, 7, 6, '2021-02-05');
INSERT INTO public.purchases VALUES (38, 8, 8, '2021-02-04');
INSERT INTO public.purchases VALUES (39, 9, 2, '2021-02-06');
INSERT INTO public.purchases VALUES (40, 9, 4, '2021-02-06');
INSERT INTO public.purchases VALUES (41, 10, 7, '2021-02-01');
INSERT INTO public.purchases VALUES (42, 11, 10, '2021-02-02');


--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 202
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.customer_id_seq', 11, true);


--
-- TOC entry 2902 (class 0 OID 0)
-- Dependencies: 204
-- Name: goods_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.goods_id_seq', 10, true);


--
-- TOC entry 2903 (class 0 OID 0)
-- Dependencies: 206
-- Name: purchases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.purchases_id_seq', 42, true);


--
-- TOC entry 2754 (class 2606 OID 16571)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2756 (class 2606 OID 16582)
-- Name: goods goods_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_pkey PRIMARY KEY (id);


--
-- TOC entry 2758 (class 2606 OID 16590)
-- Name: purchases purchases_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);


--
-- TOC entry 2759 (class 2606 OID 16591)
-- Name: purchases purchases_id_c_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_id_c_fkey FOREIGN KEY (id_c) REFERENCES public.customer(id);


--
-- TOC entry 2760 (class 2606 OID 16596)
-- Name: purchases purchases_id_g_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_id_g_fkey FOREIGN KEY (id_g) REFERENCES public.goods(id);


-- Completed on 2021-04-06 09:31:23

--
-- PostgreSQL database dump complete
--

