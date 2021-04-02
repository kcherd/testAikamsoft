--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.3

-- Started on 2021-04-02 17:43:11

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
-- TOC entry 2896 (class 0 OID 0)
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
-- TOC entry 2897 (class 0 OID 0)
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
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 206
-- Name: purchases_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;


--
-- TOC entry 2748 (class 2604 OID 16569)
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- TOC entry 2749 (class 2604 OID 16577)
-- Name: goods id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.goods ALTER COLUMN id SET DEFAULT nextval('public.goods_id_seq'::regclass);


--
-- TOC entry 2750 (class 2604 OID 16588)
-- Name: purchases id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);


--
-- TOC entry 2886 (class 0 OID 16566)
-- Dependencies: 203
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.customer (id, name, surname) VALUES (1, 'Ivan', 'Petrov');
INSERT INTO public.customer (id, name, surname) VALUES (2, 'Oleg', 'Ivanov');
INSERT INTO public.customer (id, name, surname) VALUES (3, 'Viktoria', 'Semenova');
INSERT INTO public.customer (id, name, surname) VALUES (4, 'Anna', 'Anderson');


--
-- TOC entry 2888 (class 0 OID 16574)
-- Dependencies: 205
-- Data for Name: goods; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.goods (id, name, price) VALUES (1, 'milk', 60);
INSERT INTO public.goods (id, name, price) VALUES (2, 'bread', 30);
INSERT INTO public.goods (id, name, price) VALUES (3, 'eggs', 85);
INSERT INTO public.goods (id, name, price) VALUES (4, 'cheese', 250);
INSERT INTO public.goods (id, name, price) VALUES (5, 'butter', 120);


--
-- TOC entry 2890 (class 0 OID 16585)
-- Dependencies: 207
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.purchases (id, id_c, id_g, p_date) VALUES (1, 1, 2, '2021-01-10');
INSERT INTO public.purchases (id, id_c, id_g, p_date) VALUES (2, 2, 3, '2021-01-20');
INSERT INTO public.purchases (id, id_c, id_g, p_date) VALUES (3, 3, 4, '2021-01-22');
INSERT INTO public.purchases (id, id_c, id_g, p_date) VALUES (4, 4, 5, '2021-01-30');


--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 202
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.customer_id_seq', 4, true);


--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 204
-- Name: goods_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.goods_id_seq', 5, true);


--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 206
-- Name: purchases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.purchases_id_seq', 4, true);


--
-- TOC entry 2752 (class 2606 OID 16571)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2754 (class 2606 OID 16582)
-- Name: goods goods_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_pkey PRIMARY KEY (id);


--
-- TOC entry 2756 (class 2606 OID 16590)
-- Name: purchases purchases_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);


--
-- TOC entry 2757 (class 2606 OID 16591)
-- Name: purchases purchases_id_c_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_id_c_fkey FOREIGN KEY (id_c) REFERENCES public.customer(id);


--
-- TOC entry 2758 (class 2606 OID 16596)
-- Name: purchases purchases_id_g_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_id_g_fkey FOREIGN KEY (id_g) REFERENCES public.goods(id);


-- Completed on 2021-04-02 17:43:12

--
-- PostgreSQL database dump complete
--

