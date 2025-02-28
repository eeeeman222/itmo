import React from "react";
import PropTypes from "prop-types";

const Button = ({
                    children,
                    onClick,
                    style,
                    className,
                    disabled = false,
                    type = "button",
                    ...props
                }) => {
    return (
        <button
            type={type}
            className={`custom-button ${className}`}
            style={style}
            onClick={onClick}
            disabled={disabled}
            {...props}
        >
            {children}
        </button>
    );
};


Button.propTypes = {
    children: PropTypes.node.isRequired,
    onClick: PropTypes.func,
    style: PropTypes.object,
    className: PropTypes.string,
    disabled: PropTypes.bool,
    type: PropTypes.oneOf(["button", "submit", "reset"]),
};

Button.defaultProps = {
    onClick: () => {},
    style: {},
    className: "",
    disabled: false,
    type: "button",
};

export default Button;
