--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4 (Debian 15.4-2.pgdg120+1)
-- Dumped by pg_dump version 15.4 (Debian 15.4-2.pgdg120+1)

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
-- Name: changelog; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.changelog (
    id numeric(20,0) NOT NULL,
    applied_at character varying(25) NOT NULL,
    description character varying(255) NOT NULL
);


--
-- Name: nanya; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.nanya (
    id integer NOT NULL,
    name text,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


--
-- Name: nanya_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.nanya_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: nanya_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.nanya_id_seq OWNED BY public.nanya.id;


--
-- Name: thing; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.thing (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name text,
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone
);


--
-- Name: nanya id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nanya ALTER COLUMN id SET DEFAULT nextval('public.nanya_id_seq'::regclass);


--
-- Name: changelog pk_changelog; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.changelog
    ADD CONSTRAINT pk_changelog PRIMARY KEY (id);


--
-- Name: thing thing_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.thing
    ADD CONSTRAINT thing_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

